package uk.gov.nationalarchives.tdr.virus

import java.io.{InputStream, OutputStream}

import com.amazonaws.services.lambda.runtime.{Context, RequestStreamHandler}
import io.circe
import io.circe.generic.auto._
import io.circe.parser.decode
import uk.gov.nationalarchives.tdr.core.api.{ApiClient, Input, VirusCheckApiResponse}

import scala.io.Source

class RequestHandler extends RequestStreamHandler {
  override def handleRequest(inputStream: InputStream, outputStream: OutputStream, context: Context): Unit = {
    case class VirusCheckRequest(status: String, filename: String)
    case class SnsInput(Input: VirusCheckRequest)
    val inputString = Source.fromInputStream(inputStream, "UTF-8").mkString

    print(inputString)

    for {
      inputRequest <- decode[Input](inputString)
      snsInputObj <- decode[SnsInput](inputRequest.Records.head.Sns.Message)
    } yield {
      val virusCheckRequest = snsInputObj.Input
      val status = virusCheckRequest.status
      val fileId = virusCheckRequest.filename.split("/")(1)
      val query = s"""mutation {updateVirusCheck(status: "$status", id: "$fileId")}"""
      val apiClient = new ApiClient()
      val body = apiClient.sendQueryToApi(query)
      val apiResponse: Either[circe.Error, VirusCheckApiResponse] = decode[VirusCheckApiResponse](body)
      val success = apiResponse match {
        case Right(response) => response.data.updateVirusCheck
        case Left(err) => print(err); false
      }
      outputStream.write(success.toString.getBytes())
    }
  }
}