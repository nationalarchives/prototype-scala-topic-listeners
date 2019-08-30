package uk.gov.nationalarchives.tdr.checksum

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}


object Main extends App {

  val json = """{
               |    "Records": [
               |        {
               |            "EventSource": "aws:sns",
               |            "EventVersion": "1.0",
               |            "EventSubscriptionArn": "arn:aws:sns:eu-west-2:247222723249:checksum-check-result-dev:e66c30fb-f34e-4f61-9693-c743dc1f561f",
               |            "Sns": {
               |                "Type": "Notification",
               |                "MessageId": "00df9add-e4fd-5dd6-a318-cf3f3bd8a9bf",
               |                "TopicArn": "arn:aws:sns:eu-west-2:247222723249:checksum-check-result-dev",
               |                "Subject": null,
               |                "Message": "{\"InputPath\":\"$\",\"ResultPath\":\"$\",\"OutputPath\":\"$\",\"Input\":{\"file\":\"test/27\",\"checksum\":\"d2a84f4b8b650937ec8f73cd8be2c74add5a911ba64df27458ed8229da804a26\"}}",
               |                "Timestamp": "2019-08-30T08:23:01.118Z",
               |                "SignatureVersion": "1",
               |                "Signature": "YK2zVSEGwcmg7VVqBFtIMYZbZCTyfFTFGXoWl4reLRnQ1SdS0lEard5vz3qolWPE6udgMIofS0Yrud8TCA+lJ/5ZiTKYhU+4rGrMC+CawY5V0vTMP/0uCOxd6E205ojLM2REJ4aNQqQ4gK1WSjsb0Aw+aGIHxTewNMa2u10A1O3NPRQcq70sx2HcV/r9Pii2rQttT0QrcuGTag3WnSVMSLhusHQVje7UNZCNSsci/fB8IT6mKKRnsTt+jCr+aMRuoq7pFSb2aVM3xxccGlFjXqVjtNpzTDqSoXaUP69i2gO6mYgPRmfRQ3GHDhKHUP6tVznj5Qq0HWnER6kwLI1dOQ==",
               |                "SigningCertUrl": "https://sns.eu-west-2.amazonaws.com/SimpleNotificationService-6aad65c2f9911b05cd53efda11f913f9.pem",
               |                "UnsubscribeUrl": "https://sns.eu-west-2.amazonaws.com/?Action=Unsubscribe&SubscriptionArn=arn:aws:sns:eu-west-2:247222723249:checksum-check-result-dev:e66c30fb-f34e-4f61-9693-c743dc1f561f",
               |                "MessageAttributes": {}
               |            }
               |        }
               |    ]
               |}""".stripMargin

  val requestHandler = new RequestHandler()
  requestHandler.handleRequest(new ByteArrayInputStream(json.getBytes), new ByteArrayOutputStream(),null)
}
