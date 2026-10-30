package tn.esprit.autoloc;

import tn.esprit.autoloc.exception.ResourceNotFoundException;

public class RessouceNotFoundExpecetion extends ResourceNotFoundException {
    public RessouceNotFoundExpecetion(String ressource, String id) {
        super(ressource, id);
    }
}
