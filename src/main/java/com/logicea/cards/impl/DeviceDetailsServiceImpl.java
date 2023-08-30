package com.chris.security.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chris.security.exceptions.BadRequestException;
import com.chris.security.exceptions.NotFoundException;
import com.chris.security.modal.DeviceChild;
import com.chris.security.modal.DeviceDetails;
import com.chris.security.modal.DeviceStatus;
import com.chris.security.repository.DeviceChildRepository;
import com.chris.security.repository.DeviceDetailsRepository;
import com.chris.security.service.DeviceDetailsService;

@Service
public class DeviceDetailsServiceImpl implements DeviceDetailsService {

	@Autowired
	private DeviceDetailsRepository deviceRepository;

	@Autowired
	private DeviceChildRepository deviceChildRepository;

	@Override
	public DeviceDetails findDeviceDetailsByDeviceId(String deviceId) {
		Optional<DeviceDetails> optional = deviceRepository.findDeviceDetailsByDeviceId(deviceId);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new NotFoundException();
		}
	}

	@Override
	public List<DeviceDetails> findDeviceDetailsByUserId(String userId) {
		List<DeviceDetails> devices = new ArrayList<>();
		devices.addAll(deviceRepository.findDeviceDetailsByUserId(userId));
		return devices;
	}

	@Override
	public DeviceStatus getDeviceStatus(String deviceId) {

		Optional<DeviceDetails> optional = Optional.of(findDeviceDetailsByDeviceId(deviceId));

		if (optional.isPresent()) {
			return optional.get().getStatus().get(0);
		} else {
			throw new NotFoundException();
		}

	}

	@Override
	public DeviceDetails saveDeviceDetails(DeviceDetails details) {
		String deviceId = UUID.randomUUID().toString();
		details.setDeviceId(deviceId);
		List<DeviceStatus> statusList = new ArrayList<>();
		DeviceStatus deviceStatus = new DeviceStatus();
		deviceStatus.setStatusId(details.getId());
		deviceStatus.setReportedLost(0);
		deviceStatus.setCurrentStatus("active");
		deviceStatus.setLastActive(Timestamp.from(Instant.now()));
		statusList.add(deviceStatus);
		details.setStatus(statusList);
		return deviceRepository.save(details);
	}

	@Override
	public DeviceStatus updateDeviceStatus(String deviceId, Map<String, Object> data) {

		Optional<DeviceDetails> optional = Optional.of(findDeviceDetailsByDeviceId(deviceId));

		if (optional.isPresent()) {

			DeviceStatus deviceStatus = optional.get().getStatus().get(0);

			if (data.get("batteryLife") != null) {
				deviceStatus.setBatteryLife(Integer.valueOf(data.get("batteryLife").toString()));
			}

			if (data.get("reportedLost") != null) {
				deviceStatus.setReportedLost(Integer.valueOf(data.get("reportedLost").toString()));
			}

			if (data.get("howLost") != null) {
				deviceStatus.setHowLost((data.get("howLost").toString()));
			}

			if (Integer.valueOf(data.get("reportedLost").toString()) == 1) {
				deviceStatus.setTimeReported(Timestamp.from(Instant.now()));
			}

			if (data.get("placeReported") != null) {
				deviceStatus.setPlaceReported(data.get("placeReported").toString());
			}

			if (data.get("lostReport") != null) {
				deviceStatus.setLostReport(data.get("lostReport").toString());
			}

			if (data.get("obFileUrl") != null) {
				deviceStatus.setObFileUrl(data.get("obFileUrl").toString());
			}

			if (data.get("currentStatus") != null) {
				deviceStatus.setCurrentStatus(data.get("currentStatus").toString());
			}

			if (data.get("lastActive") != null) {
				deviceStatus.setLastActive(Timestamp.from(Instant.now()));
			}

			return deviceRepository.save(optional.get()).getStatus().get(0);

		} else {
			throw new NotFoundException();
		}

	}

	@Override
	public List<DeviceChild> findDeviceChildrenByDeviceId(String deviceId) {
		Optional<DeviceDetails> optional = Optional.of(findDeviceDetailsByDeviceId(deviceId));

		if (optional.isPresent()) {
			return optional.get().getChildren();
		} else {
			throw new NotFoundException();
		}
	}

	@Override
	public DeviceChild saveDeviceChild(String deviceId, DeviceChild deviceChild) {
		Optional<DeviceDetails> optional = Optional.of(findDeviceDetailsByDeviceId(deviceId));

		if (optional.isPresent() && Optional.of(findDeviceDetailsByDeviceId(deviceChild.getDeviceId())).isPresent()) {

			Optional<DeviceChild> optionalChild = deviceChildRepository
					.findDeviceChildByDeviceId(deviceChild.getDeviceId());

			if (optionalChild.isEmpty()) {
				List<DeviceChild> children = optional.get().getChildren();
				children.add(deviceChild);
				optional.get().setChildren(children);
				return deviceRepository.save(optional.get()).getChildren().stream()
						.reduce((c, c1) -> c.equals(deviceChild) ? c : c1).get();
			} else {
				throw new BadRequestException("Device already assigned");
			}

		} else {
			throw new NotFoundException();
		}
	}

	@Override
	public void removeDeviceChild(String deviceId) {
		Optional<DeviceChild> optional = deviceChildRepository.findDeviceChildByDeviceId(deviceId);

		if (optional.isPresent()) {
			deviceChildRepository.delete(optional.get());
		} else {
			throw new NotFoundException();
		}

	}
}
