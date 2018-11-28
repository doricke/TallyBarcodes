
//  Author:     Darrell O. Ricke, Ph.D.  (mailto: Darrell.Ricke@ll.mit.edu)
//  Copyright:  Copyright (c) 2018 Massachusetts Institute of Technology 
//  License:    GNU GPL license (http://www.gnu.org/licenses/gpl.html)  
//
// DISTRIBUTION STATEMENT A. Approved for public release. Distribution is unlimited.
//
// This material is based upon work supported under Air Force Contract No. FA8702-15-D-0001.
// Any opinions, findings, conclusions or recommendations expressed in this material are those 
// of the author(s) and do not necessarily reflect the views of the U.S. Air Force.
//
// Copyright © 2018 Massachusetts Institute of Technology.
//
// The software/firmware is provided to you on an As-Is basis
//
// Delivered to the U.S. Government with Unlimited Rights, as defined in DFARS Part 252.227-7013 
// or 7014 (Feb 2014). Notwithstanding any copyright notice, U.S. Government rights in this 
// work are defined by DFARS 252.227-7013 or DFARS 252.227-7014 as detailed above. Use of this 
// work other than as specifically authorized by the U.S. Government may violate any copyrights 
// that exist in this work.
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation, either version 2 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

// import java.io._
import scala.collection.mutable.Map

// ****************************************************************************
// This class models a FASTA sequence with a name, description, and sequence
class FastaSequence extends Serializable {

  // **************************************************************************
  val annotation = collection.mutable.Map[String, String]() // sequence annotation
  var name: String = "" // current sequence name
  var description: String = "" // current sequence description
  var sequence: String = "" // current sequence 

  // **************************************************************************
  // Return the length of the sequence
  def get_length: Int = sequence.length

  // **************************************************************************
  // Compliment the DNA base
  def compliment(base: Char): Char = {
    base match {
      case 'A' => 'T'
      case 'a' => 't'
      case 'C' => 'G'
      case 'c' => 'g'
      case 'G' => 'C'
      case 'g' => 'c'
      case 'T' => 'A'
      case 't' => 'a'
      case 'N' => 'N'
      case 'n' => 'n'
      case 'R' => 'Y'
      case 'r' => 'y'
      case 'Y' => 'R'
      case 'y' => 'r'
      case '.' => '.'
      case '-' => '-'
      case _ => '?'
    } // match
  } // compliment

  // **************************************************************************
  // Reverse compliment the DNA sequence.
  def reverse_compliment: String = {
    var seq_reverse = new StringBuilder()
    for (i <- sequence.length - 1 to 0 by -1)
      seq_reverse += compliment(sequence.charAt(i))
    seq_reverse.toString()
  } // reverse_compliment

  // **************************************************************************
  // Parse the FASTA header line for name and description.
  def parseHeader(line: String, delim: Char) {
    // Check for no header line.
    if (line.length <= 0) {
      return
    } // if

    // Check for invalid header line.
    if (line.charAt(0) != delim) {
      return
    } // if

    var index1: Int = line.indexOf(' ')
    var index2: Int = line.indexOf('\t')
    if ((index2 > 0) && (index2 < index1)) index1 = index2
    if ((index1 < 0) && (index2 > 0)) index1 = index2

    if (index1 < 0)
      name = line.substring(1).trim()
    else {
      name = line.substring(1, index1)

      if (index1 + 1 < line.length)
        description = line.substring(index1 + 1).trim()
    } // if
  } // method parseHeader

  // **************************************************************************
  // Parse the FASTA header line for standardard ">" header marker.
  def parseHeader(line: String) { parseHeader(line, '>') }

  // **************************************************************************
  // Format the current sequence with <width> bases per line.
  def to_block(str: String, width: Int): String = {
    var block: String = ""

    if ((str == null) || (str.length < 1)) { "" }
    else {
      var str_start: Int = 0
      var str_end: Int = width
      while (str_end < str.length) {
        block += str.substring(str_start, str_end) + "\n"
        str_start = str_end
        str_end += width
      } // while

      block += str.substring(str_start) + "\n"
      block
    } // if
  } // to_block

  // **************************************************************************
  // Format the current sequence with 50 bases per line.
  def to_block: String = to_block(sequence, 50)

  // **************************************************************************
  // Parse key=value annotation in the FASTA description field.
  def snpAnnotation: collection.mutable.Map[String, String] = {

    val pieces = description.split( '|' )
    pieces foreach { case (piece) =>
      val tokens = piece.split( "=" )
      if ( tokens.size == 2 ) {
        annotation(tokens(0) ) = tokens(1)  // .filterNot( '"' )
      }  // if
    }  // foreach

    annotation
  }  // snpAnnotation

  // **************************************************************************
  // Extract the SNP name from standard GenBank dbSNP header line.
  // >gnl|dbSNP|rs11908623
  def snpName : String = {
    val tokens = name.split( '|' )
    tokens( tokens.size-1 )
  }  // snpName

  // **************************************************************************
  // Format the current sequence in standard FASTA format.
  def to_string: String = { ">" + name + " " + description + "\n" + to_block }

  // **************************************************************************
} // class FastaSequence
