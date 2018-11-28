
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

import scala.collection.mutable.{ ArrayBuffer, Map }

// ****************************************************************************
// Iterator class for reading FASTA sequences from a library file.
class FastaIterator(file_name: String) extends InputFile(file_name) {

  private[this] var fasta: FastaSequence = null		// current FASTA entry

  // **************************************************************************
  // Accessor method to get the current FASTA entry
  def get_fasta: FastaSequence = fasta

  // **************************************************************************
  // Read in all of the FASTA entries into an array.
  def fastas(): ArrayBuffer[FastaSequence] = {
    val seqs: ArrayBuffer[FastaSequence] = ArrayBuffer()

    // Read in all of the sequences
    while (end_of_file == false) {
      next
      seqs += fasta
    } // while

    seqs
  } // fastas

  // **************************************************************************
  // Micro RNA (miRNA) method to read all FASTA entries into a hash Map and convert to DNA.
  def micro_rnas(species: String): Map[String, String] = {
    val seqs = collection.mutable.Map[String, String]()

    // Read in all of the sequences
    while (end_of_file == false) {
      next
      if (fasta.description contains species) {
        fasta.sequence = fasta.sequence.replace('U', 'T')
        seqs += (fasta.sequence -> fasta.name)
      } // if
    } // while

    seqs
  } // micro_rnas

  // **************************************************************************
  // Iterator method to return the next FASTA entry
  def next: FastaSequence = {
    fasta = new FastaSequence()
    fasta.parseHeader(line)
    readSequence()
    fasta
  } // method next

  // **************************************************************************
  // Utility method to count the number of FASTA entries in a file.
  def countSequences: Int = {
    var count: Int = 0			// FASTA entry counter

    next_line
    while (end_of_file == false) {
      if (line.charAt(0) == '>')
        count += 1
      next_line
    } // while

    count
  } // method countSequences

  // **************************************************************************
  // Read in the sequence that follows the FASTA header.
  def readSequence() {
    var seq: String = ""		// sequence

    next_line
    while ((end_of_file == false) && (line.charAt(0) != '>')) {
      if (line.charAt(0) != '>') {
        seq += line.toUpperCase().filterNot( _ == ' ' )  // .delete( ' ' )
        next_line
      } // if
    } // while

    fasta.sequence = seq
  } // method readSequence

  // **************************************************************************

} // class FastaIterator
