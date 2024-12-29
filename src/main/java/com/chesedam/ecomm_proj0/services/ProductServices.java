package com.chesedam.ecomm_proj0.services;
import java.io.IOException;
import java.util.List;
import com.chesedam.ecomm_proj0.model.Product;
import com.chesedam.ecomm_proj0.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductServices {
    @Autowired
    private ProductRepo repo;
    public List<Product> getAllProducts(){
           return     repo.findAll();
    }
    public Product getProductById(int id)
    {
        return repo.findById(id).get();
    }
    public Product addProduct(Product product, MultipartFile imageFile) throws IOException{
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repo.save(product);
    }
    public Product updateProduct(int id,Product product,MultipartFile imagefile)throws  IOException
    {
        product.setImageData(imagefile.getBytes());
        product.setImageType(imagefile.getContentType());
        product.setImageName(imagefile.getOriginalFilename());
        return repo.save(product);
    }
    public void deleteProduct(int id) {
         repo.deleteById(id);
    }
    public List<Product> searchProducts(String Keyword)
    {
        return repo.searchProducts( Keyword);
    }
}
