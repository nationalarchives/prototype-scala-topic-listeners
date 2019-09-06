package uk.gov.nationalarchives.tdr.fileformat

import java.io.{InputStream, OutputStream}

import com.amazonaws.services.lambda.runtime.{Context, RequestStreamHandler}
import io.circe
import io.circe.generic.auto._
import io.circe.parser.decode
import uk.gov.nationalarchives.tdr.core.api.{ApiClient, ChecksumApiResponse, FileFormatApiResponse, Input}

import scala.io.Source

class RequestHandler extends RequestStreamHandler {
  override def handleRequest(inputStream: InputStream, outputStream: OutputStream, context: Context): Unit = {
    case class Identifiers(name: String, details: String)
    case class Matches(ns: String, id: String, format: String, version: String, mime: String, basis: String, warning: String)
    case class Files(filename: String, filesize: Double, modified: String, errors: String, matches: List[Matches])
    case class FileFormat(siegfried: String, scandate: String, signature: String, created: String, identifiers: List[Identifiers], files: List[Files])
    case class SnsInput(Input: FileFormat)

    val inputString = Source.fromInputStream(inputStream, "UTF-8").mkString

    print(inputString)

    for {
      inputRequest <- decode[Input](inputString)
      snsInputObj <- decode[SnsInput](inputRequest.Records.head.Sns.Message)
    } yield {
      val fileFormatRequest =  snsInputObj.Input
      val file = fileFormatRequest.files.head
      val success = file.matches.forall(fileMatch => {
        val query = s"""mutation {updateFileFormat(pronomId: "${fileMatch.id}", id: "${file.filename}")}"""
        val apiClient = new ApiClient()
        val body = apiClient.sendQueryToApi(query)

        val apiResponse: Either[circe.Error, FileFormatApiResponse] = decode[FileFormatApiResponse](body)
        apiResponse match {
          case Right(response) => response.data.updateFileFormat
          case Left(err) => print(err); false
        }
      })
      outputStream.write(success.toString.getBytes())
    }
  }
}
