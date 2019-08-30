package uk.gov.nationalarchives.tdr.core.api

case class ChecksumApiResponse(data: UpdateChecksum)
case class UpdateChecksum(updateServerSideFileChecksum: Boolean)

case class VirusCheckApiResponse(data: UpdateVirusCheck)
case class UpdateVirusCheck(updateVirusCheck: Boolean)

case class FileFormatApiResponse(data: UpdateFileFormat)
case class UpdateFileFormat(updateFileFormat: Boolean)
