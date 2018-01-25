package oneapp.incture.workbox.pmc.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.incture.pmc.poadapter.services.UserDetailsDto;
import com.incture.pmc.poadapter.services.UserDto;

import oneapp.incture.workbox.inbox.dto.ResponseMessage;
import oneapp.incture.workbox.pmc.dto.RoleOfPmcDto;
import oneapp.incture.workbox.pmc.dto.UserWorkloadDetailsDto;
import oneapp.incture.workbox.pmc.dto.responses.GroupInfoDtoResponse;
import oneapp.incture.workbox.pmc.dto.responses.RoleInfoResponse;
import oneapp.incture.workbox.pmc.dto.responses.UserDetailsResponse;
import oneapp.incture.workbox.pmc.dto.responses.UserDtoResponse;
import oneapp.incture.workbox.pmc.dto.responses.UserGroupDtoResponse;
import oneapp.incture.workbox.pmc.wsdlconsumers.UMEManagementEngineConsumer;

@Path("/user")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class UserManagementRest {

	/*
	 * @EJB UserManagementFacadeWsdlConsumerLocal user;
	 */
	UMEManagementEngineConsumer umeConsumer = new UMEManagementEngineConsumer();

	@GET
	@Path("/groups")
	public UserGroupDtoResponse getAllGroups() {
		return umeConsumer.getAllUserGroup();
	}

	@GET
	@Path("/roles")
	public RoleInfoResponse getAllRoles() {
		return umeConsumer.getAllUserRole();
	}

	@GET
	@Path("/users/{userSearch}")
	public UserDtoResponse getAllUsers(@PathParam("userSearch") String userSearch) {
		return umeConsumer.getAllUsers(userSearch);
	}

	@GET
	@Path("/pmcUserRole")
	public RoleOfPmcDto getParticularUserRoleByUserId() {
		return umeConsumer.getPmcUserRolesByLoggedInUser();
	}

	@GET
	@Path("/info/{userId}")
	public UserWorkloadDetailsDto getUserInformation(@PathParam("userId") String userId) {
		return umeConsumer.getUserInformation(userId);
	}

	@GET
	@Path("/login")
	public UserDetailsDto getLoggedInUser() {
		return umeConsumer.getLoggedInUser();
	}

	@GET
	@Path("/userGroupById/{userId}")
	public GroupInfoDtoResponse getUserGroupsById(@PathParam("userId") String userId) {
		return umeConsumer.getUserGroupByuserId(userId);
	}

	@GET
	@Path("/usersByRole/{roleUniqueName}")
	public UserDetailsResponse getUsersByRoleUniqueName(@PathParam("roleUniqueName") String roleUniqueName) {
		return umeConsumer.getUsersByRole(roleUniqueName);
	}

	@GET
	@Path("/userRoleById/{userId}")

	public RoleInfoResponse getUserRoleByUserId(@PathParam("userId") String userId) {
		return umeConsumer.getUserRoleByuserId(userId);

	}

	@GET
	@Path("/getUsersAssignedInGroup/{groupId}")
	public UserDtoResponse getUserDetailsAssignedInGroup(@PathParam("groupId") String groupId) {
		return umeConsumer.getUserDetailsAssignedInGroup(groupId);
	}

	@GET
	@Path("/getUserIdsAssignedInGroup/{groupId}")
	public UserDtoResponse getUsersAssignedInGroup(@PathParam("groupId") String groupId) {
		UserDtoResponse userDtoResponse = new UserDtoResponse();
		ResponseMessage responseMessage = new ResponseMessage();
		List<String> stringList = umeConsumer.getUsersAssignedInGroup(groupId);
		List<UserDto> userDtoList = new ArrayList<UserDto>();
		for (String st : stringList) {
			UserDto dto = new UserDto();
			dto.setLoginId(st);
			userDtoList.add(dto);
		}
		responseMessage.setMessage("Users Fetched Sucessfully");
		responseMessage.setStatus("SUCCESS");
		responseMessage.setStatusCode("1");
		userDtoResponse.setResponseMessage(responseMessage);
		userDtoResponse.setUserDtos(userDtoList);
		return userDtoResponse;
	}
}
