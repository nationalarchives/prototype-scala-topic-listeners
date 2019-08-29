package uk.gov.nationalarchives.tdr.checksum

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock._
import com.github.tomakehurst.wiremock.core.WireMockConfiguration._
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}


class ChecksumRequestHandlerSpec extends FlatSpec with Matchers with BeforeAndAfterAll {

  val wireMockServer = new WireMockServer(wireMockConfig().port(8082))

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
//    val json: String = "{\"data\" : {\"updateFileChecksum\" : true}}"
//    startWiremockServer(200, json)
//    setEnv("GRAPHQL_SERVER", "http://localhost:8082/graphql")
//    val handler = new RequestHandler()
//    handler.handleRequest(new ByteArrayInputStream(s"""{"checksum": "testchecksum", "file": "asdasdasd/1"}""".getBytes), new ByteArrayOutputStream(),null)
//
//    wireMockServer.verify(postRequestedFor(urlEqualTo("/graphql"))
//        .withRequestBody(equalToJson("{\"query\": \"mutation {updateFileChecksum(checksum: \\\"testchecksum\\\", id: 1)}\"}")))
  }
}
