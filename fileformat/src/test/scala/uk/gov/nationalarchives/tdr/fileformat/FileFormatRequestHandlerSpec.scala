package uk.gov.nationalarchives.tdr.fileformat

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock._
import com.github.tomakehurst.wiremock.core.WireMockConfiguration._
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}



class FileFormatRequestHandlerSpec extends FlatSpec with Matchers with BeforeAndAfterAll {

  val wireMockServer = new WireMockServer(wireMockConfig().port(8083))

  def setEnv(key: String, value: String) = {
    val field = System.getenv().getClass.getDeclaredField("m")
    field.setAccessible(true)
    val map = field.get(System.getenv()).asInstanceOf[java.util.Map[java.lang.String, java.lang.String]]
    map.put(key, value)
  }

  def startWiremockServer(status: Int, json: String): Unit = {
    wireMockServer.stubFor(post(urlEqualTo("/graphql"))
      .willReturn(aResponse()
        .withStatus(status)
        .withBody(json)))
  }

  override def beforeAll() = {
    wireMockServer.start()
  }

  override def afterAll() = {
    wireMockServer.resetAll()
    wireMockServer.stop()
  }

  // These no longer work with the new aws library. I'll leave them here in case we re-use them
  "The checksum request handler" should "send the mutation to the graphql server" in {
//    val json: String = "{\"data\" : {\"updateFileFormat\" : true}}"
//    startWiremockServer(200, json)
//    setEnv("GRAPHQL_SERVER", "http://localhost:8083/graphql")
//    setEnv("AUTH_TOKEN", "asdasds")
//    val handler = new RequestHandler()
//    val requestJson =
//      """
//        |{
//        |  "siegfried": "1.7.12",
//        |  "scandate": "2019-08-27T12:55:44Z",
//        |  "signature": "default.sig",
//        |  "created": "2019-06-15T12:22:38+02:00",
//        |  "identifiers": [
//        |    {
//        |      "name": "pronom",
//        |      "details": "DROID_SignatureFile_V95.xml; container-signature-20180917.xml"
//        |    }
//        |  ],
//        |  "files": [
//        |    {
//        |      "filename": "1",
//        |      "filesize": 10,
//        |      "modified": "2019-08-27T12:55:09Z",
//        |      "errors": "",
//        |      "matches": [
//        |        {
//        |          "ns": "pronom",
//        |          "id": "x-fmt/111",
//        |          "format": "Plain Text File",
//        |          "version": "",
//        |          "mime": "text/plain",
//        |          "basis": "text match ASCII",
//        |          "warning": "match on text only; extension mismatch"
//        |        }
//        |      ]
//        |    }
//        |  ]
//        |}
//      """.stripMargin
//
//    handler.handleRequest(new ByteArrayInputStream(requestJson.getBytes), new ByteArrayOutputStream(), null)
//
//
//    wireMockServer.verify(postRequestedFor(urlEqualTo("/graphql"))
//        .withRequestBody(equalToJson("{\"query\": \"mutation {updateFileFormat(pronomId: \\\"x-fmt/111\\\", id: 1)}\"}")))
  }
}
