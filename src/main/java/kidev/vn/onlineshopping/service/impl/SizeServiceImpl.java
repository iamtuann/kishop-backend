package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.Size;
import kidev.vn.onlineshopping.repository.SizeRepo;
import kidev.vn.onlineshopping.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SizeServiceImpl implements SizeService {
    @Autowired
    SizeRepo sizeRepo;


    @Override
    public Size findOne(Long id) {
        return sizeRepo.getSizeById(id);
    }

    @Override
    public void create(Size size) {
        sizeRepo.save(size);
    }

    @Override
    public void update(Size size) {
        sizeRepo.save(size);
    }

    @Override
    public void delete(Size size) {
        sizeRepo.delete(size);
    }
}
