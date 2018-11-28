# TallyBarcodes
The TallyBarcodes program counts up the sequence tagging barcodes in a high throughput 
sequencing experiment FASTQ file.

Disclaimer:

  DISTRIBUTION STATEMENT A. Approved for public release. Distribution is unlimited.
  
  This material is based upon work supported under Air Force Contract No. FA8702-15-D-0001.
  Any opinions, findings, conclusions or recommendations expressed in this material are those 
  of the author(s) and do not necessarily reflect the views of the U.S. Air Force.
  
  Copyright © 2018 Massachusetts Institute of Technology.
  
  The software/firmware is provided to you on an As-Is basis
  
  Delivered to the U.S. Government with Unlimited Rights, as defined in DFARS Part 252.227-7013 
  or 7014 (Feb 2014). Notwithstanding any copyright notice, U.S. Government rights in this 
  work are defined by DFARS 252.227-7013 or DFARS 252.227-7014 as detailed above. Use of this 
  work other than as specifically authorized by the U.S. Government may violate any copyrights 
  that exist in this work.

Example run command:

  java -jar TallyBarcodes.jar <FASTQ name> <barcodes name> <max start>

  where <FASTQ name> is the name of the FASTQ library file, 
        <barcodes name> is the name of the reference barcodes file, and
        <max start> is the upper limit for start position of a barcode in a sequence.

Example barcodes file (format: <name><tab><barcode>):

    IX-01	CTAAGGTAAC
    IX-02	TAAGGAGAAC
    IX-03	AAGAGGATTC
    IX-04	TACCAAGATC
    IX-05	CAGAAGGAAC
    IX-06	CTGCAAGTTC
    IX-07	TTCGTGATTC
    IX-08	TTCCGATAAC
    IX-09	TGAGCGGAAC
    IX-10	CTGACCGAAC
    IX-11	TCCTCGAATC
    IX-12	TAGGTGGTTC
    IX-13	TCTAACGGAC
    IX-14	TTGGAGTGTC
    IX-15	TCTAGAGGTC
    IX-16	TCTGGATGAC
    IX-17	TCTATTCGTC

Example output example:

    Barcode	Count	Name
    CTAAGGTAAC	1485	IX-01
    TAAGGAGAAC	1011	IX-02
    AAGAGGATTC	1915	IX-03
    TACCAAGATC	3721	IX-04
    CAGAAGGAAC	2398	IX-05
    CTGCAAGTTC	40157	IX-06
    TTCGTGATTC	18747391	IX-07
    TTCCGATAAC	15572870	IX-08
    TGAGCGGAAC	13775382	IX-09
    CTGACCGAAC	17082226	IX-10
    TCCTCGAATC	15414769	IX-11
    TAGGTGGTTC	65	IX-12
    TCTAACGGAC	25	IX-13
    TTGGAGTGTC	12	IX-14
    TCTAGAGGTC	649	IX-15
    TCTGGATGAC	215647	IX-16
    TCTATTCGTC	158	IX-17
