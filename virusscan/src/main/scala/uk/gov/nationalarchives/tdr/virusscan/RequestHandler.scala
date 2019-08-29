package uk.gov.nationalarchives.tdr.virusscan

import java.io.{InputStream, OutputStream}

import com.amazonaws.services.lambda.runtime.{Context, RequestStreamHandler}
import io.circe
import io.circe.generic.auto._
import io.circe.parser.decode
import uk.gov.nationalarchives.tdr.core.api.{ApiClient, VirusCheckApiResponse}

import scala.io.Source

class RequestHandler extends RequestStreamHandler {
  override def handleRequest(inputStream: InputStream, outputStream: OutputStream, context: Context): Unit = {
    case class VirusCheckRequest(status: String, filename: String)
    val inputString = Source.fromInputStream(inputStream, "UTF-8").mkString
    case class CreateVirusCheck(updateVirusCheck: Boolean)
    val parsedRequest = decode[VirusCheckRequest](inputString)

    val success = parsedRequest match {
      case Left(failure) => throw new RuntimeException(failure)
      case Right(virusCheckRequest) =>
        val status = virusCheckRequest.status
        val fileId = virusCheckRequest.filename.split("/")(1)
        val query = s"""mutation {updateVirusCheck(status: "$status", id: $fileId)}"""
        val apiClient = new ApiClient()
        val body = apiClient.sendQueryToApi(query)
        val apiResponse: Either[circe.Error, VirusCheckApiResponse] = decode[VirusCheckApiResponse](body)
        apiResponse match {
          case Right(response) => response.data.updateVirusCheck
          case Left(err) => print(err); false
        }
    }
    outputStream.write(success.toString.getBytes())
  }
}