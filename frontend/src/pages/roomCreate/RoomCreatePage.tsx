import { useState } from "react";
import { TimeDropdown } from "@/components/common/timeDropdown/TimeDropdown";
import { Time } from "@/@types/date";

const RoomCreatePage = () => {
  const now = new Date();
  const [selectedTime, setSelectedTime] = useState<Time>({
    hour: now.getHours(),
    minute: now.getMinutes(),
  });

  const handleTimeChange = (time: Time) => {
    setSelectedTime(time);
  };

  return (
    <div>
      여긴 룸 생성 페이지
      <TimeDropdown onTimeChange={handleTimeChange} selectedTime={selectedTime} />
    </div>
  );
};

export default RoomCreatePage;
