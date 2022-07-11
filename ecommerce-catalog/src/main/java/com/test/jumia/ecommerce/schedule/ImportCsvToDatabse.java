package com.test.jumia.ecommerce.schedule;

import com.test.jumia.ecommerce.entity.Product;
import com.test.jumia.ecommerce.repository.ProductRepository;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

@Component
public class ImportCsvToDatabse {
    @Autowired
    ProductRepository productRepository;

    @Scheduled(fixedDelay = 3000, initialDelay = 1000)
    public void run() throws IOException, URISyntaxException {
        List<String> paths = new ArrayList<>();
        URI uri = ImportCsvToDatabse.class.getResource("/toImport").toURI();
        Path myPath;
        if (uri.getScheme().equals("jar")) {
            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
            myPath = fileSystem.getPath("/toImport");
        } else {
            myPath = Paths.get(uri);
        }
        Stream<Path> walk = Files.walk(myPath, 1);
        for (Iterator<Path> it = walk.iterator(); it.hasNext();){
            paths.add(it.next().toString());
        }

        List<String> pathNames = new ArrayList<>();
        for(String path : paths) {
            if((path.split("/")[path.split("/").length-1]).contains("."))
                pathNames.add(path);
        }

        for(String pathName : pathNames) {
            saveInDatabase(pathName);
            Files.copy(Paths.get(pathName),
                    Paths.get(pathName.replace("/toImport", "/imported")),
                    StandardCopyOption.REPLACE_EXISTING);
        }

        for(String pathName : pathNames) {
            Files.deleteIfExists(Paths.get(pathName));
        }
    }

    private void saveInDatabase(String file) throws IOException {
        Path path = Paths.get(file);
        byte[] content = Files.readAllBytes(path);
        MultipartFile multipartFile = new org.springframework.mock.web.MockMultipartFile(file,
                file, "text/plain", content);

        InputStream inputStream = multipartFile.getInputStream();
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(false);
        CsvParser parser = new CsvParser(settings);

        List<Record> parseAllRecords = parser.parseAllRecords(inputStream);
        List<Product> products = new ArrayList<>();

        parseAllRecords.forEach(record -> {
            products.add(Product.builder()
                    .sku(record.getString("sku"))
                    .country(record.getString("country"))
                    .name(record.getString("name"))
                    .stockChange(record.getString("stock_change"))
                    .build());
        });

        products.remove(0);

        products.forEach(product -> {
            Product productFound = productRepository.findProductBySkuAndCountry(product.getSku(), product.getCountry());
            if (productFound != null) {
                if (Integer.parseInt(product.getStockChange()) != Integer.parseInt(productFound.getStockChange())) {
                    productFound.setStockChange(product.getStockChange());
                    productRepository.save(productFound);
                }
            } else {
                productRepository.save(product);
            }
        });

        System.out.println(productRepository.count());
    }
}
