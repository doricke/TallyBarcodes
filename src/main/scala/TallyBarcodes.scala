
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

//  This is the main class for TallyBarcodes.
// ******************************************************************************
object TallyBarcodes {

  //****************************************************************************
  def usage {
    println( "Program usage:" )
    println( "java -jar TallyBarcodes.jar <FASTQ name> <barcodes name> <max start>" )
    println
    println( "where <FASTQ name> is the name of a FASTQ library file," )
    println( "      <barcodes name> is the name of the reference barcodes file," )
    println( "      <max start> is the upper limit for start position of a barcode in a sequence." )
  }  // usage

  //****************************************************************************
  // This is the main method for the barcode counting program.
  def main(args: Array[String]) {
    if ( args.size == 0 )
      usage
    else {
      // First parameter is the FASTQ file name.
      val fastq_name = args( 0 )

      // Second command line parameter is the barcodes reference file name, default IonXpress.tsv
      val barcode_name = if ( args.size > 1 ) args( 1 ) else "IonXpress.tsv" 

      // Optional third parameter for upper limit of start position of barcode in sequence.
      val max_start = if ( args.size > 2 ) args( 2 ).toInt else 2

      // println( "Processing: " + fastq_name + " with " + barcode_name + " with start <= " + max_start )
  
      // Read in the sequence barcodes table.
      val barcode_reader = new TableReader(barcode_name)
      val select_barcodes = new SelectBarcodes( barcode_reader.hash_1, max_start )
 
      // Scan the FASTQ file for the table of barcodes. 
      var barcodes = select_barcodes.scan_fastq_barcodes( fastq_name )
    }  // if
  } // main

} // object TallyBarcodes
