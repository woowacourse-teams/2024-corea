import { Classification } from "@/@types/roomInfo";

export const classificationText = (text: Classification) => {
  const mapping = {
    ALL: "ALL",
    ANDROID: "AN",
    BACKEND: "BE",
    FRONTEND: "FE",
  };

  return mapping[text] || text;
};
