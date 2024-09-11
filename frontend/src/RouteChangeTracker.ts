import { hostType } from "./utils/hostType";
import { useEffect, useState } from "react";
import ReactGA from "react-ga4";
import { useLocation } from "react-router-dom";
import SECRET_KEYS from "@/config/secretKeys";

const RouteChangeTracker = () => {
  const [initialized, setInitialized] = useState(false);
  const location = useLocation();

  useEffect(() => {
    if (hostType !== "develop" && SECRET_KEYS.googleAnalytics) {
      ReactGA.initialize(SECRET_KEYS.googleAnalytics);
      setInitialized(true);
    }
  }, []);

  useEffect(() => {
    if (initialized) {
      ReactGA.set({ page: location.pathname });
      ReactGA.send("pageview");
    }
  }, [initialized, location]);
};

export default RouteChangeTracker;
