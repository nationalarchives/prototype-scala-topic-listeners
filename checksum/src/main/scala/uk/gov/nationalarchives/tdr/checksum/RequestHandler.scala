package uk.gov.nationalarchives.tdr.checksum

import java.io.{InputStream, OutputStream}

import com.amazonaws.services.lambda.runtime.{Context, RequestStreamHandler}
import io.circe
import io.circe.generic.auto._
import io.circe.parser.decode
import uk.gov.nationalarchives.tdr.core.api.{ApiClient, ChecksumApiResponse, Input}

import scala.io.Source

class RequestHandler extends RequestStreamHandler {
  override def handleRequest(inputStream: InputStream, outputStream: OutputStream, context: Context): Unit = {

    case class SnsInput(Input: ChecksumRequest)
    case class ChecksumRequest(checksum: String, file: String)

    val inputString = Source.fromInputStream(inputStream, "UTF-8").mkString

    print(inputString)

    for {
      inputRequest <- decode[Input](inputString)
      snsInputObj <- decode[SnsInput](inputRequest.Records.head.Sns.Message)
    } yield {
      val checksumRequest = snsInputObj.Input
      val checksum = checksumRequest.checksum
      val fileId = checksumRequest.file.split("/")(1)
      val query = s"""mutation Bob {updateServerSideFileChecksum(checksum: "$checksum", id: "$fileId")}"""
      val apiClient = new ApiClient()
      val body = apiClient.sendQueryToApi(query)
      val apiResponse: Either[circe.Error, ChecksumApiResponse] = decode[ChecksumApiResponse](body)
      val success: Boolean = apiResponse match {
        case Right(response) => response.data.updateServerSideFileChecksum
        case Left(err) => print(err); false
      }
      outputStream.write(success.toString.getBytes())
    }
  }
}
