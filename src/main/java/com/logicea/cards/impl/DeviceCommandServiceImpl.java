package com.chris.security.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chris.security.exceptions.NotFoundException;
import com.chris.security.modal.DeviceCommand;
import com.chris.security.modal.DeviceDetails;
import com.chris.security.repository.DeviceCommandRepository;
import com.chris.security.service.DeviceCommandService;
import com.chris.security.service.DeviceDetailsService;

@Service
public class DeviceCommandServiceImpl implements DeviceCommandService {

	@Autowired
	private DeviceCommandRepository deviceCommandRepository;

	@Autowired
	private DeviceDetailsService deviceService;

	@Override
	public List<DeviceCommand> findDeviceCommandByDeviceId(String deviceId) {
		Optional<DeviceDetails> optional = Optional.of(deviceService.findDeviceDetailsByDeviceId(deviceId));

		if (optional.isPresent()) {
			List<DeviceCommand> commands = new ArrayList<>();
			commands.addAll(deviceCommandRepository.findDeviceCommandByDeviceId(deviceId));
			return commands;
		} else {
			throw new NotFoundException();
		}
	}

	@Override
	public DeviceCommand sendDeviceCommand(String deviceId, DeviceCommand deviceCommand) {
		Optional<DeviceDetails> optional = Optional.of(deviceService.findDeviceDetailsByDeviceId(deviceId));

		if (optional.isPresent()) {
			deviceCommand.setTimeCreated(Timestamp.from(Instant.now()));
			deviceCommand.setDeviceId(deviceId);
			deviceCommand.setDevice(optional.get());
			return deviceCommandRepository.save(deviceCommand);
		} else {
			throw new NotFoundException();
		}
	}

}
