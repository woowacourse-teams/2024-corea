import { Classification } from "@/@types/roomInfo";

const convertClassification = (category: string): Classification => {
  switch (category) {
    case "fe":
      return "FRONTEND";
    case "be":
      return "BACKEND";
    case "an":
      return "ANDROID";
    default:
      return "ALL";
  }
};

export default convertClassification;
