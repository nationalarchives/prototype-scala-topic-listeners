package uk.gov.nationalarchives.tdr.checksum

import java.io.{InputStream, OutputStream}

import com.amazonaws.services.lambda.runtime.{Context, RequestStreamHandler}
import io.circe
import io.circe.generic.auto._
import io.circe.parser.decode
import uk.gov.nationalarchives.tdr.core.api.{ApiClient, ChecksumApiResponse}

import scala.io.Source

class RequestHandler extends RequestStreamHandler {
  override def handleRequest(inputStream: InputStream, outputStream: OutputStream, context: Context): Unit = {
    case class ChecksumRequest(checksum: String, file: String)
    val inputString = Source.fromInputStream(inputStream, "UTF-8").mkString

    val parsedRequest = decode[ChecksumRequest](inputString)

    val success = parsedRequest match {
      case Left(failure) => throw new RuntimeException(failure)
      case Right(checksumRequest) =>
        val checksum = checksumRequest.checksum
        val fileId = checksumRequest.file.split("/")(1)
        val query = s"""mutation Bob {updateServerSideFileChecksum(checksum: "$checksum", id: $fileId)}"""
        val apiClient = new ApiClient()
        val body = apiClient.sendQueryToApi(query)
        val apiResponse: Either[circe.Error, ChecksumApiResponse] = decode[ChecksumApiResponse](body)
        apiResponse match {
          case Right(response) => response.data.updateFileChecksum
          case Left(err) => print(err); false
        }

    }
    outputStream.write(success.toString.getBytes())
  }
}
