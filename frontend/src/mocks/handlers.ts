import feedbackHandler from "@/mocks/handler/feedbackHandler";
import profileHandler from "@/mocks/handler/profileHandler";
import roomHandler from "@/mocks/handler/roomHandler";

const handlers = [...roomHandler, ...profileHandler, ...feedbackHandler];

export default handlers;
