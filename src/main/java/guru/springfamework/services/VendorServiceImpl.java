package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {
    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorMapper.VendorToVendorDto(vendorRepository.findById(id).get());
    }

    @Override
    public VendorListDTO getAllVendors() {
        List<VendorDTO> vendorDTOList =  vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.VendorToVendorDto(vendor);
                    vendorDTO.setVendorUrl(VendorController.API_PATH + vendor.getId());
                    return vendorDTO;
                })
                .collect(Collectors.toList());
        return new VendorListDTO(vendorDTOList);
    }

    @Override
    public VendorDTO createVendors(VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO ret = vendorMapper.VendorToVendorDto(savedVendor);
        ret.setVendorUrl(VendorController.API_PATH + ret.getId());
        return ret;
    }

    @Override
    public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
        vendor.setId(id);
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO ret = vendorMapper.VendorToVendorDto(savedVendor);
        ret.setVendorUrl(VendorController.API_PATH + ret.getId());
        return ret;
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorRepository.findById(id).orElseGet(null);
        if(vendor == null)
            return null;
        if(vendorDTO.getName() != null){
            vendor.setName(vendorDTO.getName());
        }

        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO ret = vendorMapper.VendorToVendorDto(savedVendor);
        ret.setVendorUrl(VendorController.API_PATH + ret.getId());
        return ret;
    }

    @Override
    public void deleteVendor(Long id) {
        vendorRepository.deleteById(id);
    }
}
