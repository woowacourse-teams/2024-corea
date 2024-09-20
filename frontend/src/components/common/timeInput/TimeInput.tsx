import { InputHTMLAttributes, useState } from "react";
import useDropdown from "@/hooks/common/useDropdown";
import * as S from "@/components/common/timeInput/TimeInput.style";
import { Time } from "@/@types/date";

interface TimeInputProps extends InputHTMLAttributes<HTMLInputElement> {
  error?: boolean;
  onTimeChange: (time: Time) => void;
  initialTime: { hour: number; minute: number };
}

interface TimeInputChangeProps {
  newTime: Time;
  canCloseDropdown: boolean;
}

const TimePicker = ({
  time,
  onTimeInputChange,
}: {
  time: Time;
  onTimeInputChange: (event: TimeInputChangeProps) => void;
}) => {
  const handleHourClick = (hour: number) => {
    onTimeInputChange({ newTime: { ...time, hour }, canCloseDropdown: false });
  };

  const handleMinuteClick = (minute: number) => {
    onTimeInputChange({ newTime: { ...time, minute }, canCloseDropdown: true });
  };

  return (
    <S.TimePickerWrapper>
      <S.TimeSelector>
        {Array.from({ length: 24 }).map((_, hour) => (
          <S.TimeButton
            key={hour}
            isActive={hour === time.hour}
            onClick={() => handleHourClick(hour)}
          >
            {hour < 10 ? `0${hour}` : hour}
          </S.TimeButton>
        ))}
      </S.TimeSelector>
      <S.TimeSelector>
        {Array.from({ length: 60 }).map((_, minute) => (
          <S.TimeButton
            key={minute}
            isActive={minute === time.minute}
            onClick={() => handleMinuteClick(minute)}
          >
            {minute < 10 ? `0${minute}` : minute}
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

  const handleTimeChange = ({ newTime, canCloseDropdown }: TimeInputChangeProps) => {
    setSelectedTime(newTime);
    onTimeChange(newTime);

    if (canCloseDropdown) {
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
