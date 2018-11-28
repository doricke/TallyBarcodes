
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

import scala.collection.mutable.Map

// ****************************************************************************
// Class to read in a delimited table from a text file and create a hash Map.
class TableReader(val table_name: String) {

  val hash_1 = collection.mutable.Map[String, String]()	// Map of key:value1
  val hash_2 = collection.mutable.Map[String, String]() // Map of key:value2

  // Read in all of the text lines from the file.
  private[this] val table_file = new InputFile(table_name)
  private[this] val lines = table_file.read_contents().split("\n")

  // Map each of the lines into key:value1 and possibly key:value2
  for (i <- 0 until lines.length) {
    val tokens = lines(i).split("\t")		// split fields by delimiter
    hash_1 += (tokens(0) -> tokens(1).toUpperCase())

    // Check for key:value2 data
    if (tokens.length > 2)
      hash_2 += (tokens(0) -> tokens(2).toUpperCase())
  } // for

  // **************************************************************************

} // class TableReader
