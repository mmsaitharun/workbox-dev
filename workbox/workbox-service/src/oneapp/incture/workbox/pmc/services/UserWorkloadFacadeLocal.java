package oneapp.incture.workbox.pmc.services;

import javax.ejb.Local;

import oneapp.incture.workbox.pmc.dto.UserProcessDetailRequestDto;
import oneapp.incture.workbox.pmc.dto.UserSearchRequestDto;
import oneapp.incture.workbox.pmc.dto.UserTaskStatusResponseDto;
import oneapp.incture.workbox.pmc.dto.UserWorkloadResponseDto;
import oneapp.incture.workbox.poadapter.dto.TaskCountDto;

@Local
public interface UserWorkloadFacadeLocal {

	UserWorkloadResponseDto getUserWorkLoadHeatMap(UserSearchRequestDto request);

	TaskCountDto getUserWorkLoadTrendGraph(UserProcessDetailRequestDto request);

	UserTaskStatusResponseDto getUserWorkLoadTaskStausGraph(UserProcessDetailRequestDto request);
}
