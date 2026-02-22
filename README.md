JAWABAN DARI EKPERIMEN 1:
Buka /test/view → Muncul halaman HTML dari test.html
Tulisan besar: Ini dari @Controller

Buka /test/text → Muncul tulisan langsung di browser (tanpa HTML):
Ini dari @Controller + @ResponseBody → text langsung

Perbedaannya
/test/view → return nama template
/test/text → return text langsung

JAWABAN DARI EKSPERIMEN 2:
Apakah berhasil?
= Tidak
HTTP Status Code 
= 500
Error message:
Whitelabel Error Page
There was an unexpected error (type=Internal Server Error)
Error resolving template

Kesimpulan:
Spring akan mengembalikan error 500 Internal Server Error
karena template yang diminta tidak ditemukan

JAWABAN DARI EKSPERIMEN 3:
Jalankan semua URL berikut:

URL	Hasil
/greet = Selamat Pagi, Mahasiswa!
/greet?name=Budi = Selamat Pagi, Budi!
/greet?name=Budi&waktu=Siang = Selamat Siang, Budi!
/greet/Ani = Halo, Ani!
/greet/Ani/detail = Halo, Ani!
/greet/Ani/detail?lang=EN = Hello, Ani!

URL yang pakai @RequestParam = /greet?name=Budi

URL yang pakai @PathVariable = /greet/Ani

URL yang pakai keduanya = /greet/Ani/detail?lang=EN


JAWABAN UNTUK BAGIAN REFLEKSI:
1. @Controller digunakan untuk aplikasi berbasis MVC yang mengembalikan nama template HTML dan dirender oleh Thymeleaf.
@RestController digunakan untuk membuat REST API yang mengembalikan data langsung (JSON/String).
Jadi, @Controller untuk aplikasi web dengan tampilan halaman, sedangkan @RestController untuk web service atau backend API.

2. Menggunakan template yang reusable membuat kode lebih efisien karena tidak perlu membuat banyak file HTML dengan struktur
yang sama. Selain itu, lebih mudah dalam maintenance, mengurangi duplikasi kode, dan mengikuti prinsip DRY (Don't Repeat Yourself).

3. Controller menginject ProductService agar mengikuti arsitektur berlapis (layered architecture). Controller hanya menangani request, 
sedangkan Service menangani logika bisnis dan pengelolaan data. Jika Controller langsung mengelola data, maka kode menjadi tidak terstruktur,
sulit dikembangkan, dan sulit dirawat.

4. model.addAttribute("products", products) digunakan untuk mengirim data ke template HTML dalam @Controller.
Sedangkan return products langsung biasanya digunakan pada @RestController, dan hasilnya akan ditampilkan dalam bentuk JSON, bukan halaman HTML.

5. Jika membuka /products/abc, akan terjadi error 400 (Bad Request) karena Spring mencoba mengubah nilai "abc" menjadi tipe Long, tetapi gagal karena bukan angka.

6. Menggunakan @RequestMapping("/products") di level class membuat kode lebih ringkas dan rapi karena tidak perlu 
menuliskan /products di setiap method. Jika base path berubah, cukup ubah di satu tempat saja.

7. Kata “Model” memiliki beberapa arti:
- Model pada parameter method Controller digunakan untuk mengirim data ke View (template HTML). 
- Folder model/ berisi class representasi data. 
- Class Product merupakan model data yang merepresentasikan struktur objek dalam sistem.

