package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO custDTO = customerMapper.customerToCustomerDTO(customer);
                    custDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());
                    return custDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long Id) {
        return customerMapper.customerToCustomerDTO(customerRepository.findById(Id).get());
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO ret = customerMapper.customerToCustomerDTO(savedCustomer);
        ret.setCustomerUrl("/api/v1/customers/" + ret.getId());
        return ret;
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setId(id);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO ret = customerMapper.customerToCustomerDTO(savedCustomer);
        ret.setCustomerUrl("/api/v1/customers/" + ret.getId());
        return ret;
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(id).orElseGet(null);
        if(customer == null)
            return null;
        if(customerDTO.getFirstName() != null){
            customer.setFirstName(customerDTO.getFirstName());
        }

        if(customerDTO.getLastName() != null){
            customer.setLastName(customerDTO.getLastName());
        }

        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO ret = customerMapper.customerToCustomerDTO(savedCustomer);
        ret.setCustomerUrl("/api/v1/customers/" + ret.getId());
        return ret;
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
