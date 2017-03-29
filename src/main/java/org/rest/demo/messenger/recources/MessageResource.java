package org.rest.demo.messenger.recources;

import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.rest.demo.messenger.service.MessageService;
import org.rest.demo.messenger.model.Message;
import org.rest.demo.messenger.recources.beans.MessageFilterBean;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {
	
	MessageService messageService = new MessageService();
	
	@GET
	public List<Message> getAllMessages(@BeanParam MessageFilterBean filterBean){
		if(filterBean.getYear() > 0){
			return messageService.getAllMessagesForYear(filterBean.getYear());
		}
		if(filterBean.getStart() >= 0 && filterBean.getSize() > 0){
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		System.out.println("ss");
		return messageService.getAllMessages();
	}
	
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId")long messageId,
			@Context UriInfo uriInfo) {
		Message message = messageService.getMessage(messageId);
		String uri = uriInfo.getBaseUriBuilder().path(MessageResource.class)
		.path(Long.toString(message.getId())).build().toString();
		message.addLink(uri, "self");
		return message;
	}
	
	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) {
		Message newMessage = messageService.addMessage(message);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(newMessage.getId())).build())
		.entity(newMessage)
		.build();
	}
	
	
	
	
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId")long messageId, Message message) {
		message.setId(messageId);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public void removeMessage(@PathParam("messageId")long messageId) {
		messageService.removeMessage(messageId);
	}
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
	
}