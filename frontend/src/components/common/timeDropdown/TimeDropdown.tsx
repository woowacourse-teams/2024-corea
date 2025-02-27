import React, { InputHTMLAttributes, useEffect, useRef } from "react";
import useDropdown from "@/hooks/common/useDropdown";
import * as S from "@/components/common/timeDropdown/TimeDropdown.style";
import { formatTime } from "@/utils/dateFormatter";

interface TimeDropdownProps extends InputHTMLAttributes<HTMLInputElement> {
  selectedTime: Date;
  onTimeChange: (time: Date) => void;
  error: boolean;
}

interface TimeDropdownChangeProps {
  newTime: Date;
  canCloseDropdown: boolean;
}

const TimePicker = ({
  time,
  onTimeInputChange,
}: {
  time: Date;
  onTimeInputChange: (event: TimeDropdownChangeProps) => void;
}) => {
  const hourRef = useRef<HTMLButtonElement | null>(null);
  const minuteRef = useRef<HTMLButtonElement | null>(null);

  useEffect(() => {
    if (hourRef.current) {
      hourRef.current.scrollIntoView({ block: "start" });
    }
    if (minuteRef.current) {
      minuteRef.current.scrollIntoView({ block: "start" });
    }
  }, [time]);

  return (
    <S.TimePickerWrapper>
      <S.TimeSelector>
        {Array.from({ length: 24 }).map((_, hour) => (
          <S.TimeButton
            key={hour}
            isActive={hour === time.getHours()}
            ref={hour === time.getHours() ? hourRef : null}
            onClick={() => {
              const newTime = new Date(time);
              newTime.setHours(hour);
              onTimeInputChange({ newTime, canCloseDropdown: false });
            }}
          >
            {hour < 10 ? `0${hour}` : hour}
          </S.TimeButton>
        ))}
      </S.TimeSelector>

      <S.TimeSelector>
        {Array.from({ length: 60 }).map((_, minute) => (
          <S.TimeButton
            key={minute}
            isActive={minute === time.getMinutes()}
            ref={minute === time.getMinutes() ? minuteRef : null}
            onClick={() => {
              const newTime = new Date(time);
              newTime.setMinutes(minute);
              onTimeInputChange({ newTime, canCloseDropdown: true });
            }}
          >
            {minute < 10 ? `0${minute}` : minute}
          </S.TimeButton>
        ))}
      </S.TimeSelector>
    </S.TimePickerWrapper>
  );
};

export const TimeDropdown = ({ selectedTime, onTimeChange, error, ...rest }: TimeDropdownProps) => {
  const { isDropdownOpen, handleToggleDropdown, dropdownRef } = useDropdown();

  const handleTimeChange = ({ newTime, canCloseDropdown }: TimeDropdownChangeProps) => {
    onTimeChange(newTime);

    if (canCloseDropdown) handleToggleDropdown();
  };

  return (
    <S.TimeDropdownContainer ref={dropdownRef}>
      <S.TimeDropdownToggle
        type="text"
        value={formatTime(selectedTime)}
        onClick={handleToggleDropdown}
        placeholder="시간을 선택하세요"
        $error={error}
        readOnly
        {...rest}
      />
      {isDropdownOpen && <TimePicker time={selectedTime} onTimeInputChange={handleTimeChange} />}
    </S.TimeDropdownContainer>
  );
};
