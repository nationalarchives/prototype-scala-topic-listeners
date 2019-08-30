package uk.gov.nationalarchives.tdr.core.api

case class Input(Records: List[Record])
case class Record(Sns: Sns)
case class Sns(Message: String)
