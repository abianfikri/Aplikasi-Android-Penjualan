<?php
    $server = "localhost";
    $user   = "root";
    $pass   = "";
    $namaDB = "penjualan";

    $con =mysqli_connect($server,$user,$pass,$namaDB) or die ("Koneksi Gagal");

    $result = mysqli_query($con, "SELECT * FROM makanan");

    $json = array();

    while ($row = mysqli_fetch_assoc($result)){
        $json[] = $row;
    }

    echo json_encode($json);
    mysqli_close($con);

?>