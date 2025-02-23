package org.example

sealed trait Range

object Range {
  case object `1d` extends Range

  case object `5d` extends Range

  case object `1mo` extends Range

   case object `3mo` extends Range

   case object `6mo` extends Range

   case object `1y` extends Range

   case object `2y` extends Range

   case object `5y` extends Range

   case object `10y` extends Range

   case object `ytd` extends Range

   case object `max` extends Range

  // List of all valid values
   val values: Set[Range] = Set(`1d`, `5d`, `1mo`, `3mo`, `6mo`, `1y`, `2y`, `5y`, `10y`, `ytd`, `max`)

  // Convert a string to a valid Range (safe lookup)
  def fromString(value: String): Option[Range] = values.find(_.toString == value)
}

sealed trait DataGranularity

object DataGranularity {
   case object `1m` extends DataGranularity

   case object `2m` extends DataGranularity

   case object `5m` extends DataGranularity

   case object `15m` extends DataGranularity

   case object `30m` extends DataGranularity

   case object `60m` extends DataGranularity

   case object `90m` extends DataGranularity

   case object `1h` extends DataGranularity

   case object `1d` extends DataGranularity

   case object `5d` extends DataGranularity

   case object `1wk` extends DataGranularity

   case object `1mo` extends DataGranularity

   case object `3mo` extends DataGranularity

  // List of all valid values
   val values: Set[DataGranularity] = Set(`1m`, `2m`, `5m`, `15m`, `30m`, `60m`, `90m`, `1h`, `1d`, `5d`, `1wk`, `1mo`, `3mo`)

  // Convert a string to a valid DataGranularity (safe lookup)
  def fromString(value: String): Option[DataGranularity] = values.find(_.toString == value)
}