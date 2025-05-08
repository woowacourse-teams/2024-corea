import useToast from "./useToast";
import { useEffect, useState } from "react";
import MESSAGES from "@/constants/message";

const useNetwork = () => {
  const [isOnline, setIsOnline] = useState(navigator.onLine);
  const { showToast, closeToast } = useToast();

  const handleOnline = () => {
    setIsOnline(true);
    closeToast(MESSAGES.ERROR.OFFLINE);
    showToast(MESSAGES.SUCCESS.ONLINE, "success");
  };

  const handleOffline = () => {
    setIsOnline(false);
    showToast(MESSAGES.ERROR.OFFLINE, "error", Infinity);
  };

  useEffect(() => {
    window.addEventListener("online", handleOnline);
    window.addEventListener("offline", handleOffline);

    return () => {
      window.removeEventListener("online", handleOnline);
      window.removeEventListener("offline", handleOffline);
    };
  }, []);

  return isOnline;
};

export default useNetwork;
