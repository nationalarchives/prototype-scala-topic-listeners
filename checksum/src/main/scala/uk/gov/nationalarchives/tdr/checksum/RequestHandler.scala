package uk.gov.nationalarchives.tdr.checksum

import java.io.{InputStream, OutputStream}

import com.amazonaws.services.lambda.runtime.{Context, RequestStreamHandler}
import io.circe.generic.auto._
import io.circe.parser.decode

import uk.gov.nationalarchives.tdr.core.api.ApiClient
import scala.io.Source

class RequestHandler extends RequestStreamHandler {
  override def handleRequest(inputStream: InputStream, outputStream: OutputStream, context: Context): Unit = {
    case class ChecksumRequest(checksum: String, id: Int)
    val inputString = Source.fromInputStream(inputStream, "UTF-8").mkString

    val parsedRequest = decode[ChecksumRequest](inputString)

    parsedRequest match {
      case Left(failure) => throw new RuntimeException(failure)
      case Right(checksumRequest) =>
        val checksum = checksumRequest.checksum
        val fileId = checksumRequest.id
        val query = s"""mutation Bob {updateFileChecksum(checksum: "$checksum", id: $fileId)}"""
        val apiClient = new ApiClient()
        apiClient.sendQueryToApi(query)
    }

//    Await.result(futureResponse, Duration.Inf)
  }
}
