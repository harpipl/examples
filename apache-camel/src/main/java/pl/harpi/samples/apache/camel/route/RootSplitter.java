package pl.harpi.samples.apache.camel.route;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.harpi.samples.apache.camel.model.Product;
import pl.harpi.samples.apache.camel.model.Root;

import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

@Named("rootSplitter")
public class RootSplitter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RootSplitter.class);

    public Iterator split(Exchange exchange) {
        LOGGER.error("SPLIT!");

        Root root = exchange.getIn().getBody(Root.class);

        if (root == null) {
            throw new NullPointerException();
        }

        List<Product> products = Arrays.asList(root.getProducts());

        Map<Object, List<Product>> groupedProducts = products.stream()
                .collect(Collectors.groupingBy(Product::getGroupId));

        List<Root> splitRoots = new ArrayList<>();
        for (List<Product> productList : groupedProducts.values()) {
            Root newRoot = new Root();
            newRoot.setProducts(productList.toArray(new Product[0]));
            splitRoots.add(newRoot);
        }

        return splitRoots.iterator();
    }
}
