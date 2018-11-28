import scala.io.Source

//  This class provides an object model for an input text file.
//
//  Author:     Darrell O. Ricke, Ph.D.  (mailto: d_ricke@yahoo.com)
//  Copyright:  Copyright (c) 2011 Ricke Informatics
//  License:    GNU GPL license (http://www.gnu.org/licenses/gpl.html)  
//  Contact:   	Ricke Informatics, 37 Pilgrim Drive, Winchester, MA 01890
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

// ******************************************************************************
class InputFile(var file_name: String) {

  var end_of_file: Boolean = false 	// end of file flag
  var contents: String = "" 		// contents of text file
  var line: String = "" 		// current line of text file
  private[this] val in_file = Source.fromFile(file_name, "UTF-8")
  private[this] var cursor: Iterator[String] = in_file.getLines() // cursor for reading text lines from a file

  // ****************************************************************************
  def close_file(): Unit = {
    end_of_file = true
    in_file.close
  }  // close_file

  // ****************************************************************************
  // This method opens a text file for reading line by line.
  def open_file(): Unit = {
    end_of_file = false
    cursor = in_file.getLines()
  } // open_file

  // ****************************************************************************
  // This file returns the next line from the text file.
  def next_line(): String = {
    line = ""
    if (cursor.hasNext)
      line = cursor.next()
    else
      end_of_file = true
    line
  } // next_line    

  // ****************************************************************************
  // This method reads the entire text file into a string. 
  def read_contents(): String = {
    contents = ""
    for (line <- in_file.getLines())
      contents = contents + line + "\n"
    contents
  } // read_contents

  // ****************************************************************************

} // class InputFile

