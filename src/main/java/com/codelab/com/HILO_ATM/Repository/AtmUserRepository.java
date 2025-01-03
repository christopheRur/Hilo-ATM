package com.codelab.com.HILO_ATM.Repository;

import com.codelab.com.HILO_ATM.Model.AtmUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtmUserRepository extends JpaRepository<AtmUser,Long> {
	AtmUser findAtmUserById(long id);
	//AtmUser findAtmUserByPin(int pin);
    
}
