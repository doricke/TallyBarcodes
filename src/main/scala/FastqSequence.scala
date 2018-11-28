
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

// ****************************************************************************
// Class modeling a FASTQ sequence extends FASTA sequence class.
class FastqSequence extends FastaSequence {

  // **************************************************************************
  var quality: String = "" 		// sequence quality string
  var quality_values: Array[Int] = null // sequence quality values

  // **************************************************************************
  // Parse the quality line of the FASTQ sequence.
  def parseQuality: Array[Int] = {
    quality_values = new Array[Int](quality.length)
    for (i <- 0 until quality.length)
      quality_values(i) = quality.toInt - 33
    quality_values
  } // parseQuality

  // **************************************************************************
  // Format the sequence in standard FASTQ format.
  override def to_string(): String = {
    "@" + name + " " + description + "\n" + to_block + "+\n" + to_block(quality, 50)
  } // toString

  // **************************************************************************
} // class FastqSequence
