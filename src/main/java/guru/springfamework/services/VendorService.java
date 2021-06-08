package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;

import java.util.List;

public interface VendorService {
    VendorDTO getVendorById(Long id);
    VendorListDTO getAllVendors();
    VendorDTO createVendors(VendorDTO vendorDTO);
    VendorDTO updateVendor(Long id, VendorDTO vendorDTO);
    VendorDTO patchVendor(Long id, VendorDTO vendorDTO);
    void deleteVendor(Long id);
}
