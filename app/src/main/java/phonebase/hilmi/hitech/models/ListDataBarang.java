package phonebase.hilmi.hitech.models;

/**
 * Created by User on 12/03/2020.
 */

public class ListDataBarang {
    private String nm_barang;
    private String hrg_barang;
    private String fotoProduk;
    private String urlImage;
    private String crdDate;

    //construck
    public ListDataBarang(){

    }

    public ListDataBarang(String nm_barang, String hrg_barang, String fotoProduk, String urlImage, String crdDate) {
        this.nm_barang = nm_barang;
        this.hrg_barang = hrg_barang;
        this.fotoProduk = fotoProduk;
        this.urlImage = urlImage;
        this.crdDate = crdDate;
    }



    public String getNm_barang() {
        return nm_barang;
    }

    public void setNm_barang(String nm_barang) {
        this.nm_barang = nm_barang;
    }

    public String getHrg_barang() {
        return hrg_barang;
    }

    public void setHrg_barang(String hrg_barang) {
        this.hrg_barang = hrg_barang;
    }


    //fotoproduk
    public String getFotoProduk() {
        return fotoProduk;
    }

    public void setFotoProduk(String fotoProduk) {
        this.fotoProduk = fotoProduk;
    }

    public String getUrlImage(){
        return urlImage;
    }

    public void setUrlImage(String urlImage){
        this.urlImage = urlImage;
    }

    public String getCrdDate(){
        return crdDate;
    }

    public void setCrdDate(String crdDate){
        this.crdDate = crdDate;
    }
}
