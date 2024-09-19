import { InputHTMLAttributes, useState } from "react";
import useDropdown from "@/hooks/common/useDropdown";
import * as S from "@/components/common/timeInput/TimeInput.style";
import { Time } from "@/@types/date";

interface TimeInputProps extends InputHTMLAttributes<HTMLInputElement> {
  error?: boolean;
  onTimeChange: (time: Time) => void;
  initialTime: { hour: number; minute: number };
}

const TimePicker = ({
  time,
  onTimeInputChange,
}: {
  time: Time;
  onTimeInputChange: (newTime: Time, isMinuteClick?: boolean) => void;
}) => {
  const handleHourClick = (hour: number) => {
    onTimeInputChange({ ...time, hour }, false);
  };

  const handleMinuteClick = (minute: number) => {
    onTimeInputChange({ ...time, minute }, true);
  };

  return (
    <S.TimePickerWrapper>
      <S.TimeSelector>
        {Array.from({ length: 24 }, (_, i) => i).map((h) => (
          <S.TimeButton key={h} isActive={h === time.hour} onClick={() => handleHourClick(h)}>
            {h < 10 ? `0${h}` : h}
          </S.TimeButton>
        ))}
      </S.TimeSelector>
      <S.TimeSelector>
        {Array.from({ length: 60 }, (_, i) => i).map((m) => (
          <S.TimeButton key={m} isActive={m === time.minute} onClick={() => handleMinuteClick(m)}>
            {m < 10 ? `0${m}` : m}
          </S.TimeButton>
        ))}
      </S.TimeSelector>
    </S.TimePickerWrapper>
  );
};

export const TimeInput = ({
  error = false,
  onTimeChange,
  initialTime,
  ...rest
}: TimeInputProps) => {
  const { isOpen, handleToggleDropdown, dropdownRef } = useDropdown();

  const [selectedTime, setSelectedTime] = useState<Time>({
    hour: initialTime.hour,
    minute: initialTime.minute,
  });

  const handleTimeChange = (newTime: Time, isMinuteClick?: boolean) => {
    setSelectedTime(newTime);
    onTimeChange(newTime);
    if (isMinuteClick) {
      handleToggleDropdown();
    }
  };

  return (
    <S.TimeInputContainer ref={dropdownRef}>
      <S.TimeInputToggle
        type="text"
        value={`${selectedTime.hour < 10 ? `0${selectedTime.hour}` : selectedTime.hour}:${selectedTime.minute < 10 ? `0${selectedTime.minute}` : selectedTime.minute}`}
        onClick={handleToggleDropdown}
        placeholder="시간을 선택하세요"
        readOnly
        $error={error}
        {...rest}
      />
      {isOpen && <TimePicker time={selectedTime} onTimeInputChange={handleTimeChange} />}
    </S.TimeInputContainer>
  );
};
