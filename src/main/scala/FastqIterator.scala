
//  Author:     Darrell O. Ricke, Ph.D.  (mailto: Darrell.Ricke@ll.mit.edu)
//  Copyright:  Copyright (c) 2018 Massachusetts Institute of Technology 
//  License:    GNU GPL license (http://www.gnu.org/licenses/gpl.html)  

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

import scala.collection.mutable.ArrayBuffer

// ****************************************************************************
// Iterator class for FASTQ library files.
class FastqIterator(file_name: String) extends InputFile(file_name) {

  var fastq: FastqSequence = null

  // **************************************************************************
  // Reads all FASTQ entries into an ArrayBuffer
  def fastqs(): ArrayBuffer[FastqSequence] = {
    val seqs: ArrayBuffer[FastqSequence] = ArrayBuffer()

    // Read in all of the sequences
    while (end_of_file == false) {
      next
      seqs += fastq
    } // while

    seqs
  } // fastqs

  // **************************************************************************
  // Reads all miRNA FASTQ entries into an ArrayBuffer and converts to DNA.
  def micro_rnas(species: String): ArrayBuffer[FastqSequence] = {
    val seqs: ArrayBuffer[FastqSequence] = ArrayBuffer()

    // Read in all of the sequences
    while (end_of_file == false) {
      next
      if (fastq.description contains species) {
        println("Human: " + fastq.name + " " + fastq.description)
        fastq.sequence = fastq.sequence.replace('U', 'T')
        seqs += fastq
      } // if
    } // while

    seqs
  } // fastqs

  // **************************************************************************
  // Iterator method that returns the next FASTQ entry.
  def next(): FastqSequence = {
    fastq = new FastqSequence()

    fastq.parseHeader(line, '@')

    readEntry()
  } // method next

  // **************************************************************************
  // Method to read the FASTQ sequence, seperator line, and quality.
  def readEntry(): FastqSequence = {
    var seq: String = ""
    var qual: String = ""

    next_line()
    while ((end_of_file == false) && (line.charAt(0) != '+')) {
      if (line.charAt(0) != '+') {
        seq += line.toUpperCase()
        next_line()
      } // if
    } // while
    fastq.sequence = seq

    // Read in the quality letters.
    next_line()
    while ((end_of_file == false) && (qual.length < seq.length)) {
      qual += line
      next_line()
    } // while
    fastq.quality = qual

    if (qual.length != seq.length)
      println("*Warning* sequence length != quality length:\n" + seq + "\n" + qual + "\n")

    fastq
  } // method readEntry

  // **************************************************************************

} // class FastqIterator
