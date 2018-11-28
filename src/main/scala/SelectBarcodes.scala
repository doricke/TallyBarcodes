
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

// Class to count barcodes in high throughput sequence FASTQ library file.
class SelectBarcodes(barcodes: Map[String, String], max_start: Int ) {

  // ***************************************************************************
  // Find the barcode at the start of the current sequence.
  def find_barcode(seq_f: String): String = {
    // Only look for the barcode at the beginning of the sequence.
    val seq_f_start = if (seq_f.length > 24) seq_f.substring(0, 24) else seq_f

    // Scan for each of the barcode sequences.
    barcodes foreach { case (name, barcode_seq) =>

      // Check if this barcode is in the start of the sequence.
      if (seq_f_start contains barcode_seq) {
        val index = seq_f_start.indexOf( barcode_seq )

        // Check if this barcode starts within the specified start window.
        if ( index < max_start )
          return name

        // Expansion option for detecting overlapping barcodes at the start of sequences or artifacts.
        // if ( (index >= max_start) && (index < 9) )
          // println( " >>," + index + "," + barcode_seq + ",::," + seq_f_start )
      }  // if
    } // foreach

    null	// no qualifying barcode detected
  } // find_barcode

  // ***************************************************************************
  // Scan a FASTQ library for the set of specified barcodes.
  def scan_fastq_barcodes( fastq_name: String ) {
    val barcode_counts = collection.mutable.Map[String, Int]()  // count of each barcode

    // Initialize and set barcode counts to zero.
    barcodes.foreach { case(barcode_name, seq) =>
      barcode_counts += (barcode_name -> 0)
    }  // foreach

    // Set up the FASTQ iterator
    val fastq_iterator: FastqIterator = new FastqIterator( fastq_name )
    fastq_iterator.next_line

    // Scan for barcodes in each FASTQ entry
    while ( fastq_iterator.end_of_file == false ) {
      val fastq = fastq_iterator.next
 
      // Search this FASTQ sequence for a barcode
      val barcode_name = find_barcode( fastq.sequence )

      // Check if barcode found within start window
      if ( barcode_counts contains barcode_name )
        barcode_counts(barcode_name) += 1
      else
        // Safety code if zero initialization changed.
        if ( barcode_name != null )
          barcode_counts += (barcode_name -> 1)
    }  // for

    fastq_iterator.close_file()		// Close the file

    // Print out the barcode count summary
    println( "Name\tBarcode\tCount" )
    barcode_counts.keys.toList.sorted.foreach { case(name) => 
      println( name + "\t" + barcodes(name) + "\t" + barcode_counts(name) )
    }  // foreach

  }  // scan_fastq_barcodes

  // ***************************************************************************

} // class SelectBarcodes
