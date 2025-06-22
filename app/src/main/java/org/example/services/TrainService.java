package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Train;
import org.example.entities.User;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class TrainService {


    private List<Train> listTrains;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String TRAIN_PATH = "app/src/main/java/org/example/localDb/trains.json";

    public TrainService() throws IOException {
        loadTrains();
    }

    public void loadTrains() throws IOException {
        File file = new File(TRAIN_PATH);
        listTrains = objectMapper.readValue(file, new TypeReference<List<Train>>(){});
    }

    public List<Train> serachTrains(String sourceS,String destinationS){
        List<Train> finalListTrains = listTrains.stream().filter(cp->(cp.getStations().contains(sourceS) && cp.getStations().contains(destinationS))).toList();
        return finalListTrains;
    }

    public void printTrains(List<Train> trains){
        for(Train t:trains){
            System.out.println(t.toString());
        }
    }

    public Train findByTrainNo(String trainNo){
        Optional<Train> train = listTrains.stream().filter(tp->(Objects.equals(tp.getTrainNo(), trainNo))).findFirst();
        if(train.isPresent()){
            return train.get();
        }else{
            return null;
        }
    }

    public void saveTrainListToFile(Train train) throws IOException {
        File file = new File(TRAIN_PATH);
        objectMapper.writeValue(file,train);
    }
}
