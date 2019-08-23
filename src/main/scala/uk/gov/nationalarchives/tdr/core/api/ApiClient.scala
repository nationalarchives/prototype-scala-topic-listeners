package uk.gov.nationalarchives.tdr.core.api

import com.softwaremill.sttp.circe.{asJson, _}
import com.softwaremill.sttp.{HttpURLConnectionBackend, Id, SttpBackend, sttp, _}
import io.circe.generic.auto._


class ApiClient {

  def sendQueryToApi(queryString: String) = {
    case class Consignment(name: String)
    case class CreateConsignment(createConsignment: Consignment)
    case class Response(data: CreateConsignment)

    implicit val backend: SttpBackend[Id, Nothing] = HttpURLConnectionBackend()
    case class Query(query: String)
    val query = Query(queryString)

    val request = sttp.body(query)
      .post(uri"http://localhost:8080/graphql")
//      .header("Authorization", "eyJraWQiOiJ1WTI4OHZycGhNMFhHK3I5dndTdWVWSU40dTdQaHROazA2K1d3VEtaOHZZPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoidmlHQllDX1c4N1c1MUluUTFFcjlsQSIsInN1YiI6IjUyZmQ5YjczLTY5MjktNDExZC05ZDJkLTMzNzUwZTAwNWRkYyIsImF1ZCI6IjZiZjVicmpkNnVnZ2xyMW9oaTA1a24zaW01IiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInRva2VuX3VzZSI6ImlkIiwiYXV0aF90aW1lIjoxNTY2NTU4MzAyLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuZXUtd2VzdC0yLmFtYXpvbmF3cy5jb21cL2V1LXdlc3QtMl82TW4wTTJpOUMiLCJjb2duaXRvOnVzZXJuYW1lIjoiZ2RzdGVzdHVzZXIiLCJleHAiOjE1NjY1NjE5MDIsImlhdCI6MTU2NjU1ODMwMiwiZW1haWwiOiJzYW0ucGFsbWVyQG5hdGlvbmFsYXJjaGl2ZXMuZ292LnVrIn0.moA8af77GQrHqbiS0mKVbYzaOiqK6Yb_ZdyRvEaRA2Pf_ufQEI2JOwa-MwMyTbmVkQ17mLE7-KcD1OFBmIjgjxzG7_eLibv2d-tf_Rg6oqjt5iy_CmAozv7ebzu5sMXPNZOdKIP8D2pgkeIe2-APySOzRD6DNkGwWzcLpv2KgNY4GMk2EyrhYeChf2AW1wMDMPPo7mi25FH8bMZ_YpOHc-yJES2QL3sFm-2J75E9pz0A7UjbO1Uxe11L0cNl7spxTbRB9iDDgISTlpdlSwCFKW0FFuMxfjRGDFtrD1ELVfhsRnZeqwyOuTlEgbXMTEyZDksVVkLy8zogMGyUcVZw8A")
      .response(asJson[Response])

    request.send().body match {
      case Right(response) => response.map(r => println(r.data.createConsignment.name))
      case Left(err) => print(err)
    }
  }
}
