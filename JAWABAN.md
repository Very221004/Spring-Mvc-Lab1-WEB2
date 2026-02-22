# Jawaban Week 4 Lab

## Latihan 1

### Eksperimen 1: @Controller vs @RestController
- /test/view → Muncul halaman HTML dari test.html
  Tulisan besar: Ini dari @Controller
- /test/text → Muncul tulisan langsung di browser (tanpa HTML):
  Ini dari @Controller + @ResponseBody → text langsung
- Kesimpulan: /test/view → return nama template
  /test/text → return text langsung

### Eksperimen 2: Template Tidak Ada
- Apakah Berhasil: Tidak
- HTTP Status: 500
- Error: Whitelabel Error Page
  There was an unexpected error (type=Internal Server Error)
  Error resolving template
- Kesimpulan: Spring akan mengembalikan error 500 Internal Server Error
  karena template yang diminta tidak ditemukan

### Eksperimen 3: @RequestParam vs @PathVariable
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


### Pertanyaan Refleksi
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

7. 7. Kata “Model” memiliki beberapa arti:
- Model pada parameter method Controller digunakan untuk mengirim data ke View (template HTML).
- Folder model/ berisi class representasi data.
- Class Product merupakan model data yang merepresentasikan struktur objek dalam sistem.


## Latihan 2

### Eksperimen 1: Fragment Tidak Ada
- Apakah error?     
= Ya
  Error message:
= Error resolving fragment: "navbar-yang-salah"

KESIMPULAN: 
Jika nama fragment salah, Thymeleaf akan menampilkan error karena fragment tidak ditemukan.

### Eksperimen 2: Static Resource
- CSS masih bekerja?
= Ya
- Apakah halaman error?
= Tidak 
- Apakah CSS diterapkan?
= Tidak

KESIMPULAN:
th:href="@{}" lebih baik karena otomatis menyesuaikan context path aplikasi.
Jika file CSS tidak ada, halaman tetap tampil tetapi styling tidak diterapkan.

### Eksperimen 3: Tambahkan Halaman Statistik

- Kode StatisticsController:
package com.example.spring_mvc_lab1.controller;

import com.example.spring_mvc_lab1.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticsController {

    private final ProductService productService;

    public StatisticsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/statistics")
    public String showStatistics(Model model) {

        model.addAttribute("title", "Statistik Produk");
        model.addAttribute("totalProducts", productService.getTotalProducts());
        model.addAttribute("categoryStats", productService.getTotalPerCategory());
        model.addAttribute("mostExpensive", productService.getMostExpensiveProduct().orElse(null));
        model.addAttribute("cheapest", productService.getCheapestProduct().orElse(null));
        model.addAttribute("averagePrice", productService.getAveragePrice());
        model.addAttribute("lowStockCount", productService.countLowStockProducts());

        return "statistics";
    }
}

- Kode Template:
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title th:text="${title}">Statistik</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" th:href="@{/css/style.css}">
</head>
<body>

<div th:replace="~{fragments/layout :: navbar}"></div>

<div class="container main-content mt-4">

    <h1 class="mb-4">Statistik Produk</h1>

    <div class="card mb-3 p-3">
        <h5>Total Semua Produk</h5>
        <p class="fs-4" th:text="${totalProducts}">0</p>
    </div>

    <div class="card mb-3 p-3">
        <h5>Jumlah Produk per Kategori</h5>
        <ul>
            <li th:each="entry : ${categoryStats}">
                <strong th:text="${entry.key}">Kategori</strong> :
                <span th:text="${entry.value}">0</span>
            </li>
        </ul>
    </div>

    <div class="card mb-3 p-3">
        <h5>Produk Termahal</h5>
        <p th:text="${mostExpensive != null ? mostExpensive.name + ' - Rp ' + mostExpensive.price : 'Tidak ada'}"></p>
    </div>

    <div class="card mb-3 p-3">
        <h5>Produk Termurah</h5>
        <p th:text="${cheapest != null ? cheapest.name + ' - Rp ' + cheapest.price : 'Tidak ada'}"></p>
    </div>

    <div class="card mb-3 p-3">
        <h5>Rata-rata Harga</h5>
        <p th:text="'Rp ' + ${#numbers.formatDecimal(averagePrice, 0, 'COMMA', 0, 'POINT')}"></p>
    </div>

    <div class="card mb-3 p-3">
        <h5>Jumlah Produk dengan Stok < 20</h5>
        <p th:text="${lowStockCount}"></p>
    </div>

</div>

<div th:replace="~{fragments/layout :: footer}"></div>

</body>
</html>

### Pertanyaan Refleksi
1. Menggunakan Thymeleaf Fragment membuat kode lebih rapi, tidak duplikasi navbar/footer, dan lebih mudah di-maintain 
    karena cukup edit satu file untuk semua halaman.
2. Folder templates/ berisi file HTML yang diproses oleh Thymeleaf.
   Folder static/ berisi file statis seperti CSS, JS, dan gambar yang langsung dilayani oleh Spring Boot tanpa diproses template engine.
   CSS ada di static/ karena merupakan file statis.
3. th:replace mengganti seluruh tag dengan fragment.
   th:insert hanya memasukkan isi fragment ke dalam tag.
4. @{} digunakan agar URL menyesuaikan context path aplikasi dan lebih fleksibel saat deploy. 
5. Jika Controller langsung membuat new ProductService(), maka akan terjadi tight coupling, sulit untuk testing, dan 
   tidak mengikuti prinsip Dependency Injection.