package main.java.com.example.marketplace.buyer.repository;

import main.java.com.example.marketplace.buyer.dto.BuyerResponse;
import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.database.DataBase;

import java.util.List;

public final class BuyerRepository {


    public BuyerResponse emailExists(String email) {

        List<Buyer> buyerList = DataBase.getBuyerList();

        if (!buyerList.isEmpty())
            for (Buyer buyer : buyerList)
                if (buyer.getEmail().equals(email)) {

                    String name = buyer.getName();
                    int passwordSize = buyer.getPassword().length();

                    return new BuyerResponse(name, email, passwordSize);
                }
        return null;
    }
}