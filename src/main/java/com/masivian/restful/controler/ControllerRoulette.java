package com.masivian.restful.controler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.masivian.restful.objects.ObjectBet;
import com.masivian.restful.objects.ObjectRoulette;
import com.masivian.restful.repository.RepositoryRoulette;

@RestController
public class ControllerRoulette {
	@Autowired
	private RepositoryRoulette repositoryRoulette;	
	@GetMapping("/roulette")
	public Map <String, ObjectRoulette> findAll() {
		return repositoryRoulette.findAll();
	}
	@PostMapping("/roulette")
	public void createRoulette() {
		repositoryRoulette.createRoulette();
	}	
	@PostMapping("/open/{id}")
	public ResponseEntity<ObjectRoulette> openRoulette(@PathVariable String id) {
		ObjectRoulette roulette = getRoulette(id);
		String statusRoulette = roulette.getStatus();
		if (!statusRoulette.equals("open")) {
			roulette.setStatus("open");
			repositoryRoulette.openRoulette(roulette, id);	
			return new ResponseEntity<ObjectRoulette>(roulette , HttpStatus.OK);
		} else
			return new ResponseEntity<ObjectRoulette>(roulette , HttpStatus.CONFLICT);	
	}	
	@PostMapping("/close/{id}")
	public ResponseEntity<Map<String, String>> closeRoulette(@PathVariable String id) {
		ObjectRoulette roulette = getRoulette(id);
		roulette.setStatus("close");
		repositoryRoulette.closeRoulette(roulette, id);
		return betResults(roulette);
	}	
	public ObjectRoulette getRoulette (String id) {
		return (ObjectRoulette) repositoryRoulette.getRoulette(id);
	}	
	@PostMapping("/bet/{id}")
	public ResponseEntity<ObjectBet> createBet (@RequestBody ObjectBet objectBet, @RequestHeader (value = "IdUser") String idUser,@PathVariable String id) {
		ObjectRoulette roulette = getRoulette(id);
		objectBet.setIdUser(idUser);
		if (betChecking(roulette, objectBet)) {
			betAdding(roulette, objectBet, id);
			return new ResponseEntity<ObjectBet>(objectBet , HttpStatus.OK);
		} else return new ResponseEntity<ObjectBet>(objectBet , HttpStatus.CONFLICT);
		
	}	
	public boolean betChecking (ObjectRoulette roulette, ObjectBet objectBet) {
		if(objectBet.getColorBet()==null) objectBet.setColorBet("");		
		if (roulette.getStatus().equals("open") && 
				(objectBet.getAmountBet()>0 && objectBet.getAmountBet()<=10000) && 
				((objectBet.getNumberBet()>0 && objectBet.getNumberBet()<=36) ^ (objectBet.getColorBet().equals("black") || objectBet.getColorBet().equals("red")))) {
			return true;
		} else return false;			
	}	
	public void betAdding (ObjectRoulette roulette, ObjectBet objectBet, String idRoulette) {
		if (roulette.getArrayBets() == null) {
			ArrayList<ObjectBet> arrayBets = new ArrayList<>();
			arrayBets.add(objectBet);
			roulette.setArrayBets(arrayBets);
		} else {
			roulette.getArrayBets().add(objectBet);
		}		
		repositoryRoulette.createBet(roulette, idRoulette);
	}
	
	public ResponseEntity<Map<String, String>> betResults (ObjectRoulette roulette) {
		int  betResultNumber = (int)(Math.random()*36+1);
		Map <String, String> returnValue = new HashMap<>();		
		if(!(roulette.getArrayBets() == null)) {
			ArrayList<ObjectBet> arrayBets = roulette.getArrayBets();
			for (int i = 0; i<arrayBets.size();i++) {
				if (arrayBets.get(i).getNumberBet() == 0) {
					long result = betColorResults(arrayBets.get(i), betResultNumber);
					returnValue.put(arrayBets.get(i).getIdUser(), ""+result);
				} else {
					long result = betNumberResults(arrayBets.get(i), betResultNumber);
					returnValue.put(arrayBets.get(i).getIdUser(), ""+result);
					}
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}
	
	public long betColorResults(ObjectBet objectBet, int betResultNumber) {
		long dineroRetornado = 0;
		if (betResultNumber%2==0 && objectBet.getColorBet().equals("red") ) {
			dineroRetornado = (long) (objectBet.getAmountBet()*1.8);
		} 
		if (betResultNumber%2!=0 && objectBet.getColorBet().equals("black")) {
			dineroRetornado = (long) (objectBet.getAmountBet()*1.8);
		}
		return dineroRetornado;
	}
	
	public long betNumberResults (ObjectBet objectBet, int betResultNumber) {
		long dineroRetornado = 0;
		if (betResultNumber==objectBet.getNumberBet()) {
			dineroRetornado = (long) (objectBet.getAmountBet()*5);
		} 
		return dineroRetornado;
	}
}
