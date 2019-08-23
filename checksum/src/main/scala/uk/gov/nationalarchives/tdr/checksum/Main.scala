package uk.gov.nationalarchives.tdr.checksum

import java.io.{BufferedOutputStream, ByteArrayInputStream}

object Main extends App {

    val handler = new RequestHandler()

    handler.handleRequest(new ByteArrayInputStream("""{"checksum": "sajldajsldkjasdlksjdlkasjldkajsdlkajsd", "id": 2}""".getBytes), null, null)


}