import { useEffect, useState } from "react";
import ReactGA from "react-ga4";
import { useLocation } from "react-router-dom";
import { GOOGLE_ANALYTICS } from "@/config/googleAnalytics";

const RouteChangeTracker = () => {
  const [initialized, setInitialized] = useState(false);
  const location = useLocation();

  useEffect(() => {
    if (process.env.NODE_ENV !== "development" && GOOGLE_ANALYTICS) {
      ReactGA.initialize(GOOGLE_ANALYTICS);
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
