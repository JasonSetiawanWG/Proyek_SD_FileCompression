# Proyek_SD_FileCompression
Proyek SD:
Theme : File Compression
Data Structure used : Heap Tree dan HashMap
Objective : Reduce the size of a file for a faster data transmission and saving storage. And for certain context,it can also be used for encryption.

Basic Concept : ASCII Characters from 1-255 are represented in 8 bits/1 byte. The assigned values for each bits in the byte,needs chart/encoder/mapping to be converted as character,otherwise it just stays as an 8 bit integer. In this assignment,we are gonna shorten the size of bits from each characters,based on the frequency of each characters.

Job desk :
- Assigning Byte Nodes with the string or array of bytes input (getByteNodes method),creating MinPriorityQueue class,creating ByteNode class,
Example : Input : AABBBCCCCC
The task : 
Assign Byte with its frequency
A : 2
B : 3
C : 5
Yang mengerjakan : 1 orang(Mike) 

- Creating HuffmanTree ()
- Traverse the HuffmanTree to assign new bits to the characters,Assign HuffmanCode as HashMap
Yang mengerjakan : 1 orang(Kenenza)


- Assign the original bytes with the HuffmanCode into HuffCodeBytes
- Give an output to the HuffmanBytes or the Bytes that has been compressed
Yang mengerjakan : 1 orang(Sherleen)

- Decompress the HuffmanBytes to the original form ( decomp method and convertByteInBit method)
Yang mengerjakan : 2 orang(Jason & Joshua)

-CompressImage and DecompressImage : 1 orang (Jason)

# Class Diagram

![FileCompression_CD (1)](https://github.com/user-attachments/assets/b22dec96-6f26-4746-b10d-a195997e7a07)

# SiftUp
![Screenshot 2024-12-20 165738](https://github.com/user-attachments/assets/690ab9da-f8a6-4e73-8fa6-991a1750f2d8)
![Screenshot 2024-12-20 165748](https://github.com/user-attachments/assets/3fd39f04-33b9-4fde-a8fd-de40e2d74b74)

# Sift Down 
![Screenshot 2024-12-20 165814](https://github.com/user-attachments/assets/125c9e8e-82a4-4933-a65c-46e92a036f92)
![Screenshot 2024-12-20 165822](https://github.com/user-attachments/assets/dca821c1-991c-498f-ad00-0bb30e1c3ec3)
![Screenshot 2024-12-20 165831](https://github.com/user-attachments/assets/43d02896-a50a-4b79-88cd-8ed06818a1f3)
![Screenshot 2024-12-20 165844](https://github.com/user-attachments/assets/5185fc80-4626-43e0-a333-aed98b48a714)


# Example of HuffmanTree
![image](https://github.com/user-attachments/assets/ce1a3480-398a-44b4-adab-409385af155b)
![image](https://github.com/user-attachments/assets/6557a90b-ff9b-40ea-bb57-6b26db6733b2)

# Example of BitMap represented in Hexadecimal values
![Example of Bitmap represented in Hexadecimal Values](https://github.com/user-attachments/assets/6a2a6113-7b6f-45af-a8ab-0b7d74239afa)

