using Caliburn.Micro;
using System;
using Tickets.Models;

namespace Tickets.ViewModels
{
    public class MyTicketsPageViewModel : PropertyChangedBase
    {
        public BindableCollection<Ticket> Tickets { get; set; } = new BindableCollection<Ticket>();

        public MyTicketsPageViewModel()
        {
            Tickets.AddRange(new[]
            {
                new Ticket
                {
                    Id = "3264523",
                    CreationDate = DateTimeOffset.Now - TimeSpan.FromDays(20),
                    Type = TicketType.Theft,
                    AnswerType = TicketAnswerType.Mail
                },
                new Ticket
                {
                    Id = "56847659",
                    CreationDate = DateTimeOffset.Now - TimeSpan.FromDays(30),
                    Type = TicketType.IllegalParking,
                    AnswerType = TicketAnswerType.Mail,
                    AnswerDate = DateTimeOffset.Now - TimeSpan.FromDays(3),
                    IsAnswered = true
                },
                new Ticket
                {
                    Id = "3264520",
                    CreationDate = DateTimeOffset.Now - TimeSpan.FromDays(15),
                    Type = TicketType.Theft,
                    IsAnswered = false
                }
            });
        }
    }
}
