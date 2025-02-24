# Word Search Puzzle Solver
Disusun untuk memenuhi Tugas Kecil 1 IF2211 Strategi Algoritma "Penyelesaian *IQ Puzzle Pro* dengan Algoritma *Brute Force*".

## Daftar Isi
* [Deskripsi Singkat Program](#deskripsi-singkat-program)
* [Struktur Program](#struktur-program)
* [Requirement Program](#struktur-program)
* [Cara Kompilasi Program](#cara-kompilasi-program)
* [Cara Menjalankan Program](#cara-menjalankan-program)
* [Author](#author)

## Deskripsi Singkat Program
Program untuk mencari semua solusi kata pada *IQ Puzzle Pro* dengan menggunakan algoritma *brute force* serta ADT Matriks. Program menerima masukan dalam bentuk file teks dalam format `.txt` yang berisi konfigurasi: panjang baris dan panjang kolom board kemudian jumlah block yang akan digunakan beserta bentuk block sesuai dengan jumlah block yang diberikan. Kemudian, program akan mengeluarkan hasil pencarian tersebut dalam bentuk command prompt dengan kata yang ditemukan akan diberi warna. Program dibangun dengan menggunakan bahasa Java.

## Struktur Program
```bash
.
│   .gitignore
│   extract.bat
│   README.md
│   run.bat
│   
├───doc
│       Tugas Kecil 1 IF2211 Strategi Algoritma Penyelesaian IQ Puzzle Pro dengan Algoritma Brute Force.pdf
│       
├───src
│   │   App.java
│   │
│   ├───IO
│   │   ├───InputParser.java
│   │   └───OutputHandler.java
│   │
│   ├───DataStructure
│   │   ├───Board.java
│   │   └───Block.java
│   │ 
│   └───Solver
│       └───Solver.java
│
└───test
        testcase.txt
        solution.txt
```

## Requirement Program
Berikut adalah **prasyarat** untuk menjalankan program:

- **Java Development Kit (JDK):** Versi **11 atau lebih baru**.
- **JavaFX:** Library untuk GUI.
- **File Input:** File teks berisi konfigurasi papan dan blok (*contoh tersedia di folder test/input*).
- **Sistem Operasi:** Program dapat dijalankan di **Windows atau Linux**.

## Instalasi
1. Pastikan **JDK** dan **JavaFX** sudah terinstal di sistem Anda.
2. Clone repository ini atau **download source code program**.
   ```bash
   git clone "https://github.com/Farhanabd05/Tucil1_13523042.git"
   ```
3. Jika menggunakan **IDE** (seperti **IntelliJ IDEA** atau **Eclipse**), pastikan untuk mengkonfigurasi **JavaFX SDK** di **project settings**.

## Cara Mengkompilasi Program 
1. **Buka terminal atau command prompt.**
2. **Navigasi ke direktori project.**
3. Jalankan perintah berikut untuk mengkompilasi program:
   ```bash
   javac --module-path <path-to-javafx-sdk> --add-modules javafx.controls -d bin src\IO\*.java src\Solver\*.java src\DataStructure\*.java src\*.java
   ```
   **Ganti `<path-to-javafx-sdk>` dengan path ke JavaFX SDK** di sistem Anda.

## Cara Menjalankan Program 1
Setelah program dikompilasi, jalankan dengan perintah berikut:
```bash
   1. cd bin

   2. java --module-path <path-to-javafx-sdk> --add-modules javafx.controls App
```
**Langkah-langkah penggunaan:**
1. Klik **"Load File"** untuk memuat puzzle.
2. Pilih file teks yang berisi konfigurasi papan dan blok.
2. Klik **"Solve Puzzle"** untuk menjalankan solver.
3. Setelah solusi ditemukan, gunakan tombol **"Save Solution"** atau **"Save Solution Image"** untuk menyimpan solusi ke file teks atau gambar.

## Cara Menjalankan Program 2
1. **Buka terminal atau command prompt.**
2. **Navigasi ke direktori project.**
3. Jalankan perintah berikut untuk run program:
   ```bash
   java --module-path <path-to-javafx-sdk/lib> --add-modules javafx.controls,javafx.fxml -cp bin App
   ```
   **Ganti `<path-to-javafx-sdk>` dengan path ke JavaFX SDK** di sistem Anda.

## Cara Mengkompilasi dan Menjalankan Program dengan interface CLI
1. **Buka terminal atau command prompt.**
2. **Navigasi ke direktori project.**
1. **Buka terminal atau command prompt.**
2. **Navigasi ke direktori project.**
3. Jalankan perintah berikut untuk mengkompilasi program:
   ```bash
   javac -d bin src\IO\*.java src\Solver\*.java src\DataStructure\*.java src\*.java
   ```
4. Jalankan perintah berikut untuk run program:
   ```bash
   1. cd bin
   2. java Main
   ```
## Contoh File Input
File input harus memiliki format berikut:
```
N M P
CaseType
<Blok 1>
<Blok 2>
...
<Blok P>
```
### Contoh:
```
5 5 7
DEFAULT
AA
A
BB
B
C
CC
D
DD
EE
EE
E
FF
FF
F
GGG
```

## Author
* Nama: Abdullah Farhan
* NIM: 13523042
* Prodi/Jurusan: STEI/Teknik Informatika